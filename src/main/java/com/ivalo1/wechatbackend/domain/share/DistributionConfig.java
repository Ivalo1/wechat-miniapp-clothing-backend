package com.ivalo1.wechatbackend.domain.share;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_distribution_config")
public class DistributionConfig extends BaseEntity {
    private Integer level;           // 1一级 2二级
    private BigDecimal commissionRate; // 佣金比例
    private Integer status;
}
