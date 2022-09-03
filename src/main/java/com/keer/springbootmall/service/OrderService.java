package com.keer.springbootmall.service;

import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;

public interface OrderService {
     Order getOrderById(Integer orderId);

     Integer createdOrder(Integer userId, CreatedOrderRequest createdOrderRequest);
}
