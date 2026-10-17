package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.WxLoginDTO;
import com.ivalo1.wechatbackend.application.service.AuthAppService;
import com.ivalo1.wechatbackend.application.vo.LoginVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/mini/auth") @RequiredArgsConstructor
public class WxAuthController {
    private final AuthAppService authAppService;
    @Operation(summary = "微信登录") @PostMapping("/login")
    public ApiResponse<LoginVO> login(@Valid @RequestBody WxLoginDTO dto) { return ApiResponse.success(authAppService.wxLogin(dto)); }
}
