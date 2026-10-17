package com.ivalo1.wechatbackend.interfaces.admin;
import com.ivalo1.wechatbackend.application.dto.BannerDTO;
import com.ivalo1.wechatbackend.application.dto.AnnouncementDTO;
import com.ivalo1.wechatbackend.application.service.ContentAppService;
import com.ivalo1.wechatbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/admin/content") @RequiredArgsConstructor
public class ContentAdminController {
    private final ContentAppService contentAppService;

    @PostMapping("/banner")
    public ApiResponse<Void> createBanner(@RequestBody BannerDTO dto) {
        contentAppService.createBanner(dto);
        return ApiResponse.success();
    }

    @PostMapping("/announcement")
    public ApiResponse<Void> createAnnouncement(@RequestBody AnnouncementDTO dto) {
        contentAppService.createAnnouncement(dto);
        return ApiResponse.success();
    }
}
