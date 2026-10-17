package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class ReviewVO { private Long id; private String nickname; private String avatar; private Integer rating; private String content; private String images; private String skuName; private LocalDateTime createTime; }
