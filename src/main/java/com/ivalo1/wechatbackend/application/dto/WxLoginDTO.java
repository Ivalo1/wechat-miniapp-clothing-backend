package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotBlank; import lombok.Data;
@Data
public class WxLoginDTO { @NotBlank private String code; }
