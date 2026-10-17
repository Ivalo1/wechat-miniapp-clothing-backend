package com.ivalo1.wechatbackend.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private String status; // CREATED, PAID, SHIPPED, COMPLETED, CANCELED
    private LocalDateTime createdAt;
    private List<OrderItem> items;
}
