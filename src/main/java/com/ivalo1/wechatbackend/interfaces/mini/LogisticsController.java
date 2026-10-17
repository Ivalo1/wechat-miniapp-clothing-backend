package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.vo.LogisticsVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.domain.logistics.LogisticsInfo;
import com.ivalo1.wechatbackend.infrastructure.logistics.LogisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController @RequestMapping("/mini/logistics") @RequiredArgsConstructor
public class LogisticsController {
    private final LogisticsService logisticsService;

    @GetMapping("/{orderNo}")
    public ApiResponse<LogisticsVO> query(@PathVariable String orderNo,
        @RequestParam String company, @RequestParam String trackingNo) {
        LogisticsInfo info = logisticsService.queryLogistics(company, trackingNo);
        LogisticsVO vo = new LogisticsVO();
        vo.setCompany(info.getCompany()); vo.setTrackingNo(info.getTrackingNo());
        vo.setStatus(info.getStatus());
        vo.setTraces(info.getTraces().stream().map(t -> {
            LogisticsVO.TraceVO tv = new LogisticsVO.TraceVO();
            tv.setTime(t.getTime()); tv.setDescription(t.getDescription());
            return tv;
        }).collect(Collectors.toList()));
        return ApiResponse.success(vo);
    }
}
