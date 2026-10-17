package com.ivalo1.wechatbackend.interfaces.mini;
import com.ivalo1.wechatbackend.common.ApiResponse;
import com.ivalo1.wechatbackend.infrastructure.oss.OssService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController @RequestMapping("/mini/file") @RequiredArgsConstructor
public class FileController {
    private final OssService ossService;

    @PostMapping("/upload")
    public ApiResponse<String> upload(@RequestParam("file") MultipartFile file) {
        return ApiResponse.success(ossService.upload(file));
    }
}
