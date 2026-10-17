package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class RefundApplyDTO {
    @NotNull private Long orderItemId;
    @NotNull private Integer type; // 1仅退款 2退货退款 3换货
    @NotBlank private String reason;
    private String description;
    private String images;
}
