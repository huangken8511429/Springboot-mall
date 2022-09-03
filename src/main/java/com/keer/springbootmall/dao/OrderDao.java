package com.keer.springbootmall.dao;

import com.keer.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createdOrder(Integer userId,Integer totalAmount);

    void createdOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
