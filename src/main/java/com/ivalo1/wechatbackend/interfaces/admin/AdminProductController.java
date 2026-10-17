package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.application.dto.ProductDTO;
import com.ivalo1.wechatbackend.application.service.ProductAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/admin/product") @RequiredArgsConstructor
public class AdminProductController {
    private final ProductAppService productAppService;
    @Operation(summary = "新增商品") @PostMapping
    public ApiResponse<Long> save(@Valid @RequestBody ProductDTO dto) { return ApiResponse.success(productAppService.saveProduct(dto)); }
}
