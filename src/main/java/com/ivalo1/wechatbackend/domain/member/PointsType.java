package com.ivalo1.wechatbackend.domain.member;

import lombok.Getter;

@Getter
public enum PointsType {
    SIGN_IN(1, "签到"),
    CONSUME(2, "消费"),
    REFUND_DEDUCT(3, "退款扣减");

    private final int code;
    private final String desc;

    PointsType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
