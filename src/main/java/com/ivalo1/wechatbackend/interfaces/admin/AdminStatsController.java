package com.ivalo1.wechatbackend.interfaces.admin;

import com.ivalo1.wechatbackend.application.service.StatsAppService;
import com.ivalo1.wechatbackend.application.vo.StatsVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "管理端-统计报表")
@RestController
@RequestMapping("/admin/stats")
@RequiredArgsConstructor
public class AdminStatsController {

    private final StatsAppService statsAppService;

    @Operation(summary = "数据概览")
    @GetMapping("/overview")
    public ApiResponse<StatsVO> overview() {
        return ApiResponse.success(statsAppService.getDashboardStats());
    }

    @Operation(summary = "近30天销售趋势")
    @GetMapping("/trend/30d")
    public ApiResponse<List<StatsVO.DailySalesVO>> trend30d() {
        return ApiResponse.success(statsAppService.getDailySales30d());
    }
}
