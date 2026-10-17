package com.ivalo1.wechatbackend.domain.coupon;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("t_user_coupon")
public class UserCoupon {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long userId;
    private Long templateId;
    private Integer status; // 0未使用 1已使用 2已过期
    private LocalDateTime receiveTime;
    private LocalDateTime useTime;
    private LocalDateTime expireTime;

    /** 使用优惠券 */
    public void use() {
        if (this.status != CouponStatus.UNUSED.getCode()) {
            throw new BusinessException(ErrorCode.COUPON_NOT_AVAILABLE, "优惠券不可用");
        }
        if (this.expireTime != null && LocalDateTime.now().isAfter(this.expireTime)) {
            throw new BusinessException(ErrorCode.COUPON_EXPIRED);
        }
        this.status = CouponStatus.USED.getCode();
        this.useTime = LocalDateTime.now();
    }

    /** 是否可用 */
    public boolean isAvailable() {
        return this.status == CouponStatus.UNUSED.getCode()
                && (this.expireTime == null || LocalDateTime.now().isBefore(this.expireTime));
    }
}
