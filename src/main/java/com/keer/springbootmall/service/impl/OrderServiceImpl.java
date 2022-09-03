package com.keer.springbootmall.service.impl;

import com.keer.springbootmall.dao.OrderDao;
import com.keer.springbootmall.dao.ProductDao;
import com.keer.springbootmall.dao.UserDao;
import com.keer.springbootmall.dto.BuyItem;
import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.model.OrderItem;
import com.keer.springbootmall.model.Product;
import com.keer.springbootmall.model.User;
import com.keer.springbootmall.service.OrderService;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);  //這邊log用法要注意，要import org.slf4j版本

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
        //先檢查前端傳過來的userId是否是合理的userId
        User user = userDao.getUserById(userId);

        if (user == null) {
            log.warn("該userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;

        List<OrderItem> orderItemList = new ArrayList<>();
        //取得商品訂單中所有商品的ID以及數量
        for (BuyItem buyItem : createdOrderRequest.getBuyItemList()) {
            Product product = productDao.getProductById(buyItem.getProductId());
           //確認清單裡的商品是否存在
            if (product == null) {
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                //確認清單裡的商品的庫存是否足夠訂單的購買數量
            } else if (product.getStock() < buyItem.getQuantity()) {
                log.warn("商品 {} 庫存數量不足,無法購買。剩餘庫存 {} ，欲購買數量 {} ", buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //更新庫存
            productDao.updateStock(product.getProductId(),product.getStock() - buyItem.getQuantity());

            //計算總價錢
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;


            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createdOrder(userId, totalAmount);

        orderDao.createdOrderItems(orderId, orderItemList);

        return orderId;
    }
}
