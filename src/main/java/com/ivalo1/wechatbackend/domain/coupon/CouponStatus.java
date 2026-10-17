package com.ivalo1.wechatbackend.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponStatus {
    UNUSED(0, "未使用"),
    USED(1, "已使用"),
    EXPIRED(2, "已过期");

    private final int code;
    private final String desc;

    CouponStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
