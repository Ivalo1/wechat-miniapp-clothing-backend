package com.ivalo1.wechatbackend.domain.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCancelledEvent {
    private String orderNo;
    private Long userId;
}
