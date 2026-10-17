package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.RecommendAppService;
import com.ivalo1.wechatbackend.application.vo.ProductListVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/mini/recommend") @RequiredArgsConstructor
public class RecommendController {
    private final RecommendAppService recommendAppService;

    @GetMapping("/guess-you-like")
    public ApiResponse<List<ProductListVO>> guessYouLike(@RequestAttribute("userId") Long userId,
        @RequestParam(defaultValue = "20") int limit) {
        return ApiResponse.success(recommendAppService.guessYouLike(userId, limit));
    }

    @PostMapping("/behavior")
    public ApiResponse<Void> recordBehavior(@RequestAttribute("userId") Long userId,
        @RequestParam Long productId, @RequestParam String type) {
        recommendAppService.recordBehavior(userId, productId, type);
        return ApiResponse.success();
    }
}
