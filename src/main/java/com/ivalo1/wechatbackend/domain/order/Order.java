package com.ivalo1.wechatbackend.domain.order;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单聚合根
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
public class Order extends BaseEntity {

    private String orderNo;
    private Long userId;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal payAmount;
    private Integer status;
    private Long addressId;
    private String addressSnapshot;
    private String couponId;
    private String remark;
    private String payTransactionId;
    private LocalDateTime payTime;
    private LocalDateTime shipTime;
    private LocalDateTime receiveTime;
    private LocalDateTime completeTime;
    private LocalDateTime cancelTime;

    // ========== 领域行为 ==========

    /** 取消订单 */
    public void cancel() {
        if (this.status != OrderStatus.PENDING_PAYMENT.getCode()) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL, "只有待支付订单可以取消");
        }
        this.status = OrderStatus.CANCELLED.getCode();
        this.cancelTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 超时取消 */
    public void timeoutCancel() {
        if (this.status != OrderStatus.PENDING_PAYMENT.getCode()) {
            return; // 幂等：已处理则忽略
        }
        this.status = OrderStatus.TIMEOUT_CANCELLED.getCode();
        this.cancelTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 标记已支付 */
    public void markPaid(String transactionId) {
        if (this.status != OrderStatus.PENDING_PAYMENT.getCode()) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "订单状态不允许支付");
        }
        this.status = OrderStatus.PAID.getCode();
        this.payTime = LocalDateTime.now();
        this.payTransactionId = transactionId;
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 发货 */
    public void ship() {
        if (this.status != OrderStatus.PAID.getCode()) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有已支付订单可以发货");
        }
        this.status = OrderStatus.SHIPPED.getCode();
        this.shipTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 确认收货 */
    public void confirmReceive() {
        if (this.status != OrderStatus.SHIPPED.getCode()) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有已发货订单可以确认收货");
        }
        this.status = OrderStatus.RECEIVED.getCode();
        this.receiveTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 完成订单 */
    public void complete() {
        this.status = OrderStatus.COMPLETED.getCode();
        this.completeTime = LocalDateTime.now();
        this.setUpdateTime(LocalDateTime.now());
    }

    /** 计算实付金额 */
    public void calculatePayAmount() {
        this.payAmount = this.totalAmount.subtract(
                this.discountAmount != null ? this.discountAmount : BigDecimal.ZERO);
        if (this.payAmount.compareTo(BigDecimal.ZERO) < 0) {
            this.payAmount = BigDecimal.ZERO;
        }
    }

    /** 是否待支付 */
    public boolean isPendingPayment() {
        return this.status == OrderStatus.PENDING_PAYMENT.getCode();
    }

    /** 是否已支付 */
    public boolean isPaid() {
        return this.status == OrderStatus.PAID.getCode();
    }
}
