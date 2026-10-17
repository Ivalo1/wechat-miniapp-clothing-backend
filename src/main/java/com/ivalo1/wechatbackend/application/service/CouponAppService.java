package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.vo.CouponVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.coupon.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List; import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CouponAppService {
    private final CouponRepository couponRepository;

    public List<CouponVO> getAvailableCoupons(Long userId) {
        return couponRepository.findUserCoupons(userId, 0).stream().map(this::toVO).collect(Collectors.toList());
    }

    @Transactional
    public void receiveCoupon(Long userId, Long templateId) {
        CouponTemplate template = couponRepository.findTemplateById(templateId);
        if (template == null || !template.canReceive()) throw new BusinessException(ErrorCode.COUPON_NOT_AVAILABLE);
        if (couponRepository.countUserCoupon(userId, templateId) >= template.getReceiveLimit())
            throw new BusinessException(ErrorCode.COUPON_ALREADY_RECEIVED);
        template.deductRemaining();
        couponRepository.updateTemplate(template);
        UserCoupon uc = new UserCoupon();
        uc.setUserId(userId); uc.setTemplateId(templateId);
        uc.setStatus(CouponStatus.UNUSED.getCode());
        uc.setReceiveTime(LocalDateTime.now()); uc.setExpireTime(template.getEndTime());
        couponRepository.saveUserCoupon(uc);
    }

    private CouponVO toVO(UserCoupon uc) {
        CouponVO vo = new CouponVO();
        vo.setId(uc.getId()); vo.setStatus(uc.getStatus()); vo.setExpireTime(uc.getExpireTime());
        CouponTemplate t = couponRepository.findTemplateById(uc.getTemplateId());
        if (t != null) { vo.setName(t.getName()); vo.setType(t.getType()); vo.setDiscountAmount(t.getDiscountAmount()); vo.setDiscountRate(t.getDiscountRate()); vo.setMinAmount(t.getMinAmount()); }
        return vo;
    }
}
