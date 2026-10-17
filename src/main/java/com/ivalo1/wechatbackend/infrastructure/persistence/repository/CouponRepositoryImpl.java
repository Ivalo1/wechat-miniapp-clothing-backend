package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.coupon.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.CouponTemplateMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.UserCouponMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponTemplateMapper couponTemplateMapper;
    private final UserCouponMapper userCouponMapper;

    @Override
    public CouponTemplate findTemplateById(Long id) {
        return couponTemplateMapper.selectById(id);
    }

    @Override
    public UserCoupon findUserCouponById(Long id) {
        return userCouponMapper.selectById(id);
    }

    @Override
    public List<UserCoupon> findUserCoupons(Long userId, Integer status) {
        LambdaQueryWrapper<UserCoupon> w = new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId);
        if (status != null) w.eq(UserCoupon::getStatus, status);
        return userCouponMapper.selectList(w);
    }

    @Override
    public void saveTemplate(CouponTemplate template) {
        couponTemplateMapper.insert(template);
    }

    @Override
    public void updateTemplate(CouponTemplate template) {
        couponTemplateMapper.updateById(template);
    }

    @Override
    public void saveUserCoupon(UserCoupon userCoupon) {
        userCouponMapper.insert(userCoupon);
    }

    @Override
    public void updateUserCoupon(UserCoupon userCoupon) {
        userCouponMapper.updateById(userCoupon);
    }

    @Override
    public long countUserCoupon(Long userId, Long templateId) {
        return userCouponMapper.selectCount(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getTemplateId, templateId));
    }
}
