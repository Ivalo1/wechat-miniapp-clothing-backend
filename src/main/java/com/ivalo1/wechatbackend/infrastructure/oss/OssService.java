package com.ivalo1.wechatbackend.infrastructure.oss;
import com.aliyun.oss.OSS;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class OssService {
    private final OSS ossClient;
    @Value("${aliyun.oss.bucket-name}") private String bucketName;
    @Value("${aliyun.oss.endpoint}") private String endpoint;

    public String upload(MultipartFile file) {
        try {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String key = "clothing/" + date + "/" + UUID.randomUUID().toString().replace("-","") + ext;
            ossClient.putObject(bucketName, key, file.getInputStream());
            return "https://" + bucketName + "." + endpoint + "/" + key;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
    }
}
