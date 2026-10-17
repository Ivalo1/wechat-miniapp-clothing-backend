package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.dto.RefundApplyDTO;
import com.ivalo1.wechatbackend.application.service.RefundAppService;
import com.ivalo1.wechatbackend.application.vo.RefundVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/mini/refund") @RequiredArgsConstructor
public class RefundController {
    private final RefundAppService refundAppService;

    @PostMapping("/apply")
    public ApiResponse<RefundVO> applyRefund(@Valid @RequestBody RefundApplyDTO dto, @RequestAttribute("userId") Long userId) {
        return ApiResponse.success(refundAppService.applyRefund(userId, dto));
    }

    @GetMapping("/list")
    public ApiResponse<List<RefundVO>> list(@RequestParam(required = false) Integer status, @RequestAttribute("userId") Long userId) {
        return ApiResponse.success(refundAppService.getUserRefunds(userId, status));
    }

    @PostMapping("/cancel/{refundId}")
    public ApiResponse<Void> cancel(@PathVariable Long refundId) {
        return ApiResponse.success();
    }
}
