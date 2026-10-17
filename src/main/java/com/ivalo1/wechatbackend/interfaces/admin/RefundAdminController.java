package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.application.service.RefundAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/admin/refund") @RequiredArgsConstructor
public class RefundAdminController {
    private final RefundAppService refundAppService;

    @PostMapping("/{refundId}/approve")
    public ApiResponse<Void> approve(@PathVariable Long refundId) {
        refundAppService.approveRefund(refundId);
        return ApiResponse.success();
    }

    @PostMapping("/{refundId}/reject")
    public ApiResponse<Void> reject(@PathVariable Long refundId, @RequestParam String reason) {
        refundAppService.rejectRefund(refundId, reason);
        return ApiResponse.success();
    }

    @PostMapping("/{refundId}/complete")
    public ApiResponse<Void> complete(@PathVariable Long refundId) {
        refundAppService.completeRefund(refundId);
        return ApiResponse.success();
    }
}
