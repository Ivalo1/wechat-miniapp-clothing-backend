package com.ivalo1.wechatbackend.domain.coupon;

import lombok.Getter;

@Getter
public enum CouponType {
    FULL_REDUCTION(1, "满减券"),
    DISCOUNT(2, "折扣券");

    private final int code;
    private final String desc;

    CouponType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
