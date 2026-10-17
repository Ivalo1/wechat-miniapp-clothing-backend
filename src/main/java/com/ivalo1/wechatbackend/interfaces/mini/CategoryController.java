package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.CategoryAppService;
import com.ivalo1.wechatbackend.application.vo.CategoryTreeVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/mini/category") @RequiredArgsConstructor
public class CategoryController {
    private final CategoryAppService categoryAppService;
    @Operation(summary = "分类树") @GetMapping("/tree")
    public ApiResponse<List<CategoryTreeVO>> tree() { return ApiResponse.success(categoryAppService.getCategoryTree()); }
}
