package com.ivalo1.wechatbackend.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivalo1.wechatbackend.domain.coupon.CouponTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CouponTemplateMapper extends BaseMapper<CouponTemplate> {

    @Update("UPDATE t_coupon_template SET remaining_count = remaining_count - 1 WHERE id = #{id} AND remaining_count > 0")
    int deductRemaining(@Param("id") Long id);
}
