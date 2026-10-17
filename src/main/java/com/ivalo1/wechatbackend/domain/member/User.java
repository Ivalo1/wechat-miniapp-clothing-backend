package com.ivalo1.wechatbackend.domain.member;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户聚合根
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class User extends BaseEntity {

    private String openid;
    private String unionid;
    private String nickname;
    private String avatar;
    private Integer gender;
    private String phone;
    private Integer points;
    private Long levelId;
    private BigDecimal totalConsumed;
    private LocalDate lastSignInDate;

    // ========== 领域行为 ==========

    /** 增加积分 */
    public void addPoints(int points) {
        this.points = (this.points != null ? this.points : 0) + points;
    }

    /** 扣减积分 */
    public void deductPoints(int points) {
        if (this.points == null || this.points < points) {
            throw new com.ivalo1.wechatbackend.common.BusinessException(
                    com.ivalo1.wechatbackend.common.ErrorCode.POINTS_NOT_ENOUGH);
        }
        this.points -= points;
    }

    /** 增加累计消费 */
    public void addConsumed(java.math.BigDecimal amount) {
        this.totalConsumed = (this.totalConsumed != null ? this.totalConsumed : java.math.BigDecimal.ZERO).add(amount);
    }

    /** 是否今日已签到 */
    public boolean hasSignedToday() {
        return this.lastSignInDate != null && this.lastSignInDate.equals(LocalDate.now());
    }

    /** 记录签到日期 */
    public void recordSignIn() {
        this.lastSignInDate = LocalDate.now();
        this.setUpdateTime(LocalDateTime.now());
    }
}
