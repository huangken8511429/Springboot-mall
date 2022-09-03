package com.keer.springbootmall.dao;

import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    List<OrderItem> getOrderItemByOrderId(Integer orderId);

    Order getOrderById(Integer orderId);

    Integer createdOrder(Integer userId,Integer totalAmount);

    void createdOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
