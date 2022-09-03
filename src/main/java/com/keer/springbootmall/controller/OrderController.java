package com.keer.springbootmall.controller;

import com.keer.springbootmall.constant.OrderQueryParams;
import com.keer.springbootmall.dto.CreatedOrderRequest;
import com.keer.springbootmall.model.Order;
import com.keer.springbootmall.service.OrderService;
import com.keer.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
@Validated
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    )
    {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderQueryParams);

        Integer count = orderService.countOrder(orderQueryParams);

        Page<Order> page = new Page<>();
        page.setTotal(count);
        page.setOffset(offset);
        page.setLimit(limit);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }


    @PostMapping("/users/{userId}/orders")  //先登入才能創建訂單
    public ResponseEntity<?> createdOrder(@PathVariable Integer userId,
                                          @RequestBody @Valid CreatedOrderRequest createdOrderRequest){
        Integer orderId = orderService.createdOrder(userId,createdOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    };

}
