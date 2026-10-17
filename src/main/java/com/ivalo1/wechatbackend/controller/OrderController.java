package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.model.Order;
import com.ivalo1.wechatbackend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<Order> create(@RequestBody Order order) {
        Order o = orderService.createOrder(order);
        return ApiResponse.ok(o);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Order>> listByUser(@PathVariable Long userId) {
        return ApiResponse.ok(orderService.listByUser(userId));
    }
}
