package com.ivalo1.wechatbackend.domain.refund;
import lombok.Getter;

@Getter
public enum RefundType {
    ONLY_REFUND(1, "仅退款"),
    RETURN_AND_REFUND(2, "退货退款"),
    EXCHANGE(3, "换货");

    private final int code;
    private final String desc;
    RefundType(int code, String desc) { this.code = code; this.desc = desc; }
}
