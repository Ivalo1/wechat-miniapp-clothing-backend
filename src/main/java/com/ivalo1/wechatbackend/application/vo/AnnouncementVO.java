package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class AnnouncementVO { private Long id; private String title; private String content; private LocalDateTime publishTime; }
