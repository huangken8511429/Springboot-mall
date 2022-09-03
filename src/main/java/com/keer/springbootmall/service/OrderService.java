package com.keer.springbootmall.service;

import com.keer.springbootmall.constant.OrderQueryParams;
import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

     Integer countOrder(OrderQueryParams orderQueryParams);

     List<Order> getOrders(OrderQueryParams orderQueryParams);
     Order getOrderById(Integer orderId);

     Integer createdOrder(Integer userId, CreatedOrderRequest createdOrderRequest);
}
