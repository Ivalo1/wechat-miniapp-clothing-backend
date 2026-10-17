package com.ivalo1.wechatbackend.controller;

import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/notify")
    public ApiResponse<Void> notify(@RequestBody Map<String, Object> payload) {
        // This is a placeholder for payment provider callback handling (e.g., WeChat Pay)
        // Expected payload contains orderId and status
        try {
            if (!payload.containsKey("orderId")) {
                return ApiResponse.error("missing orderId");
            }
            Long orderId = Long.valueOf(String.valueOf(payload.get("orderId")));
            String status = String.valueOf(payload.getOrDefault("status", "PAID"));
            orderService.updateOrderStatus(orderId, status);
            return ApiResponse.ok();
        } catch (Exception e) {
            return ApiResponse.error("invalid payload");
        }
    }
}
