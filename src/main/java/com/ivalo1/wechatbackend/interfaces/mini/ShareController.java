package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.ShareAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/mini/share") @RequiredArgsConstructor
public class ShareController {
    private final ShareAppService shareAppService;

    @PostMapping("/generate")
    public ApiResponse<String> generateShareCode(@RequestAttribute("userId") Long userId, @RequestParam Long productId) {
        return ApiResponse.success(shareAppService.generateShareCode(userId, productId));
    }

    @PostMapping("/click")
    public ApiResponse<Void> recordClick(@RequestParam String shareCode, @RequestAttribute("userId") Long userId) {
        shareAppService.recordClick(shareCode, userId);
        return ApiResponse.success();
    }
}
