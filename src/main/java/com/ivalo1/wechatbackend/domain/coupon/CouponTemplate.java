package com.ivalo1.wechatbackend.domain.coupon;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_coupon_template")
public class CouponTemplate extends BaseEntity {
    private String name;
    private Integer type; // 1满减 2折扣
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minAmount;
    private Integer totalCount;
    private Integer remainingCount;
    private Integer receiveLimit;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer status;

    /** 是否可领取 */
    public boolean canReceive() {
        LocalDateTime now = LocalDateTime.now();
        return this.status != null && this.status == 1
                && this.remainingCount > 0
                && now.isAfter(startTime) && now.isBefore(endTime);
    }

    /** 扣减库存 */
    public void deductRemaining() {
        if (this.remainingCount <= 0) {
            throw new BusinessException(ErrorCode.COUPON_NOT_AVAILABLE, "优惠券已领完");
        }
        this.remainingCount--;
    }

    /** 计算优惠金额 */
    public BigDecimal calculateDiscount(BigDecimal orderAmount) {
        if (orderAmount.compareTo(this.minAmount) < 0) {
            return BigDecimal.ZERO;
        }
        if (this.type == CouponType.FULL_REDUCTION.getCode()) {
            return this.discountAmount;
        } else {
            BigDecimal discounted = orderAmount.multiply(this.discountRate);
            return orderAmount.subtract(discounted).setScale(2, java.math.RoundingMode.HALF_UP);
        }
    }
}
