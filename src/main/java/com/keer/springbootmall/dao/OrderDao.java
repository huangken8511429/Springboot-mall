package com.keer.springbootmall.dao;

import com.keer.springbootmall.constant.OrderQueryParams;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Order getOrderById(Integer orderId);

    Integer createdOrder(Integer userId, Integer totalAmount);

    void createdOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
