package com.ivalo1.wechatbackend.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wechat.pay")
public class WxPayProperties {
    private String appid;
    private String mchId;
    private String mchKey;
    private String notifyUrl;
    private String certPath;
    private String keyPath;
}
