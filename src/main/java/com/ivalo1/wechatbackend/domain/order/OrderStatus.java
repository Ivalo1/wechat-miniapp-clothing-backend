package com.ivalo1.wechatbackend.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING_PAYMENT(0, "待支付"),
    PAID(1, "已支付/待发货"),
    SHIPPED(2, "已发货"),
    RECEIVED(3, "已收货"),
    COMPLETED(4, "已完成"),
    CANCELLED(5, "已取消"),
    TIMEOUT_CANCELLED(6, "超时取消"),
    REFUNDING(7, "退款中"),
    REFUNDED(8, "已退款");

    private final int code;
    private final String desc;

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderStatus of(int code) {
        for (OrderStatus s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Invalid OrderStatus code: " + code);
    }
}
