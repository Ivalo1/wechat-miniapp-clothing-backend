package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.CouponAppService;
import com.ivalo1.wechatbackend.application.vo.CouponVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/mini/coupon") @RequiredArgsConstructor
public class CouponController {
    private final CouponAppService couponAppService;
    @Operation(summary = "我的优惠券") @GetMapping("/list")
    public ApiResponse<List<CouponVO>> list(Authentication auth) { return ApiResponse.success(couponAppService.getAvailableCoupons((Long)auth.getPrincipal())); }
    @Operation(summary = "领取") @PostMapping("/receive/{templateId}")
    public ApiResponse<Void> receive(Authentication auth, @PathVariable Long templateId) { couponAppService.receiveCoupon((Long)auth.getPrincipal(), templateId); return ApiResponse.success(); }
}
