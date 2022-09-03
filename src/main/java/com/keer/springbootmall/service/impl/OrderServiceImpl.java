package com.keer.springbootmall.service.impl;

import com.keer.springbootmall.dao.OrderDao;
import com.keer.springbootmall.dao.ProductDao;
import com.keer.springbootmall.dto.BuyItem;
import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.model.OrderItem;
import com.keer.springbootmall.model.Product;
import com.keer.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemByOrderId(orderId);

          //一張訂單裡面會包含多個orderItem
        order.setOrderItemList(orderItemList);

        return order;
    }

    @Transactional //因為有修改多張Table，故加上@Transactional，方便之後發生Exception，Rollback資料庫操作，確保兩個table是同時發生，或同時失敗。
    @Override
    public Integer createdOrder(Integer userId, CreatedOrderRequest createdOrderRequest) {
        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();
             //取得商品訂單中所有商品的ID以及數量
        for (BuyItem buyItem : createdOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價錢
           int  amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;


            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createdOrder(userId,totalAmount);

        orderDao.createdOrderItems(orderId,orderItemList);

        return orderId;
    }
}
