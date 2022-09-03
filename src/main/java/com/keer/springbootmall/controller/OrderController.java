package com.keer.springbootmall.controller;

import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/users/{userId}/orders")  //先登入才能創建訂單
    public ResponseEntity<?> createdOrder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreatedOrderRequest createdOrderRequest){
        Integer orderId = orderService.createdOrder(userId,createdOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    };

}
