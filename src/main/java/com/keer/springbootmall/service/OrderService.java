package com.keer.springbootmall.service;

import com.keer.springbootmall.dto.CreatedOrderRequest;

public interface OrderService {

     Integer createdOrder(Integer userId, CreatedOrderRequest createdOrderRequest);
}
