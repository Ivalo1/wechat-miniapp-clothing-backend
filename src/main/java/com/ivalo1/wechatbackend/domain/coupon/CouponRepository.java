package com.ivalo1.wechatbackend.domain.coupon;

public interface CouponRepository {
    CouponTemplate findTemplateById(Long id);
    UserCoupon findUserCouponById(Long id);
    java.util.List<UserCoupon> findUserCoupons(Long userId, Integer status);
    void saveTemplate(CouponTemplate template);
    void updateTemplate(CouponTemplate template);
    void saveUserCoupon(UserCoupon userCoupon);
    void updateUserCoupon(UserCoupon userCoupon);
    long countUserCoupon(Long userId, Long templateId);
}
