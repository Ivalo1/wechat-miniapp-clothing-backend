package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.application.dto.AdminLoginDTO;
import com.ivalo1.wechatbackend.application.service.AuthAppService;
import com.ivalo1.wechatbackend.application.vo.LoginVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/admin/auth") @RequiredArgsConstructor
public class AdminAuthController {
    private final AuthAppService authAppService;
    @Operation(summary = "管理员登录") @PostMapping("/login")
    public ApiResponse<LoginVO> login(@Valid @RequestBody AdminLoginDTO dto) { return ApiResponse.success(authAppService.adminLogin(dto)); }
}
