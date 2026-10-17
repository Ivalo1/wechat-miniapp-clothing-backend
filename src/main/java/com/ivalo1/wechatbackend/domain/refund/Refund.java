package com.ivalo1.wechatbackend.domain.refund;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 售后单聚合根
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_refund")
public class Refund extends BaseEntity {
    private String refundNo;
    private Long userId;
    private Long orderId;
    private Long orderItemId;
    private String orderNo;
    private Integer type;       // 1仅退款 2退货退款 3换货
    private Integer status;     // 0待审核 1已同意 2已拒绝 3已退货 4已退款 5已取消
    private String reason;
    private String description;
    private String images;      // JSON数组
    private BigDecimal refundAmount;
    private String returnTrackingNo;
    private String returnCompany;
    private String rejectReason;
    private LocalDateTime refundTime;

    /** 申请售后 */
    public static Refund apply(Long userId, Long orderId, Long orderItemId, String orderNo,
                                int type, String reason, String desc, String images, BigDecimal amount) {
        Refund r = new Refund();
        r.userId = userId; r.orderId = orderId; r.orderItemId = orderItemId;
        r.orderNo = orderNo; r.type = type; r.status = RefundStatus.PENDING.getCode();
        r.reason = reason; r.description = desc; r.images = images;
        r.refundAmount = amount;
        r.setCreateTime(LocalDateTime.now()); r.setUpdateTime(LocalDateTime.now());
        return r;
    }

    /** 审核通过 */
    public void approve() {
        if (this.status != RefundStatus.PENDING.getCode())
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "售后单状态不允许操作");
        this.status = RefundStatus.APPROVED.getCode();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 审核拒绝 */
    public void reject(String rejectReason) {
        if (this.status != RefundStatus.PENDING.getCode())
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "售后单状态不允许操作");
        this.status = RefundStatus.REJECTED.getCode();
        this.rejectReason = rejectReason;
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 确认退货收货 */
    public void confirmReturn(String trackingNo, String company) {
        if (this.status != RefundStatus.APPROVED.getCode())
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "售后单状态不允许操作");
        this.status = RefundStatus.RETURNED.getCode();
        this.returnTrackingNo = trackingNo;
        this.returnCompany = company;
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 完成退款 */
    public void completeRefund() {
        this.status = RefundStatus.REFUNDED.getCode();
        this.refundTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 取消 */
    public void cancel() {
        if (this.status != RefundStatus.PENDING.getCode())
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "售后单状态不允许操作");
        this.status = RefundStatus.CANCELLED.getCode();
        this.setUpdateTime(LocalDateTime.now());
    }

    public boolean isPending() { return this.status == RefundStatus.PENDING.getCode(); }
    public boolean isRefunded() { return this.status == RefundStatus.REFUNDED.getCode(); }
}
