package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.application.service.ContentAppService;
import com.ivalo1.wechatbackend.application.vo.BannerVO;
import com.ivalo1.wechatbackend.application.vo.AnnouncementVO;
import com.ivalo1.wechatbackend.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/mini/content") @RequiredArgsConstructor
public class ContentController {
    private final ContentAppService contentAppService;

    @GetMapping("/banners")
    public ApiResponse<List<BannerVO>> getBanners(@RequestParam(required = false) Integer position) {
        return ApiResponse.success(contentAppService.getBanners(position));
    }

    @GetMapping("/announcements")
    public ApiResponse<List<AnnouncementVO>> getAnnouncements() {
        return ApiResponse.success(contentAppService.getAnnouncements());
    }
}
