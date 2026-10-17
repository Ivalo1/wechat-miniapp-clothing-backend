package com.ivalo1.wechatbackend.domain.member;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_member_level")
public class MemberLevel extends BaseEntity {
    private String name;
    private Integer level;
    private BigDecimal minConsumed;
    private Integer discountRate; // 折扣率 如 95 表示 9.5折
    private Integer dailyPointsLimit;
}
