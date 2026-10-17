package com.ivalo1.wechatbackend.domain.refund;
import lombok.Getter;

@Getter
public enum RefundStatus {
    PENDING(0, "待审核"),
    APPROVED(1, "已同意"),
    REJECTED(2, "已拒绝"),
    RETURNED(3, "已退货"),
    REFUNDED(4, "已退款"),
    CANCELLED(5, "已取消");

    private final int code;
    private final String desc;
    RefundStatus(int code, String desc) { this.code = code; this.desc = desc; }
}
