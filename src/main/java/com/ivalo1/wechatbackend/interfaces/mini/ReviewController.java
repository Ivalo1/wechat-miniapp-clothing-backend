package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.ReviewDTO;
import com.ivalo1.wechatbackend.application.service.ReviewAppService;
import com.ivalo1.wechatbackend.application.vo.ReviewVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/mini/review") @RequiredArgsConstructor
public class ReviewController {
    private final ReviewAppService reviewAppService;

    @PostMapping
    public ApiResponse<Void> createReview(@Valid @RequestBody ReviewDTO dto,
        @RequestParam Long productId, @RequestParam Long orderId, @RequestAttribute("userId") Long userId) {
        reviewAppService.createReview(userId, productId, orderId, dto);
        return ApiResponse.success();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ReviewVO>> getProductReviews(@PathVariable Long productId,
        @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(reviewAppService.getProductReviews(productId, page, size));
    }
}
