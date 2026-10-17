package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/admin/user")
public class AdminUserController {
    @Operation(summary = "用户列表") @GetMapping("/list")
    public ApiResponse<Object> list() { return ApiResponse.success(null); } // TODO
}
