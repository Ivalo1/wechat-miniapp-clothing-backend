package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.MemberAppService;
import com.ivalo1.wechatbackend.application.vo.MemberInfoVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.infrastructure.lock.IdempotentLock;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/mini/member") @RequiredArgsConstructor
public class MemberController {
    private final MemberAppService memberAppService;
    @Operation(summary = "会员信息") @GetMapping("/info")
    public ApiResponse<MemberInfoVO> info(Authentication auth) { return ApiResponse.success(memberAppService.getMemberInfo((Long)auth.getPrincipal())); }
    @Operation(summary = "签到") @PostMapping("/sign-in")
    @IdempotentLock(key="'sign_in:'+#auth.principal", message="今日已签到")
    public ApiResponse<Void> signIn(Authentication auth) { memberAppService.signIn((Long)auth.getPrincipal()); return ApiResponse.success(); }
}
