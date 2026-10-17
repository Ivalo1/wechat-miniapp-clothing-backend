package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.application.dto.ShipDTO;
import com.ivalo1.wechatbackend.application.service.OrderAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/admin/order") @RequiredArgsConstructor
public class AdminOrderController {
    private final OrderAppService orderAppService;
    @Operation(summary = "发货") @PostMapping("/ship")
    public ApiResponse<Void> ship(@RequestBody ShipDTO dto) { orderAppService.shipOrder(dto.getOrderNo()); return ApiResponse.success(); }
}
