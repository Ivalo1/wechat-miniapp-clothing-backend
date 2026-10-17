package com.ivalo1.wechatbackend.domain.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderCreatedEvent {
    private String orderNo;
    private Long userId;
}
