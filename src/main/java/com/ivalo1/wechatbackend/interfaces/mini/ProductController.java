package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.ProductAppService;
import com.ivalo1.wechatbackend.application.vo.ProductDetailVO;
import com.ivalo1.wechatbackend.application.vo.ProductListVO;
import com.ivalo1.wechatbackend.common.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/mini/product") @RequiredArgsConstructor
public class ProductController {
    private final ProductAppService productAppService;
    @Operation(summary = "商品列表") @GetMapping("/list")
    public ApiResponse<PageResult<ProductListVO>> list(@RequestParam(defaultValue="1") int page, @RequestParam(defaultValue="10") int size, @RequestParam(required=false) Long categoryId, @RequestParam(required=false) String keyword) { return ApiResponse.success(productAppService.getProductList(page, size, categoryId, keyword)); }
    @Operation(summary = "商品详情") @GetMapping("/{id}")
    public ApiResponse<ProductDetailVO> detail(@PathVariable Long id) { return ApiResponse.success(productAppService.getProductDetail(id)); }
}
