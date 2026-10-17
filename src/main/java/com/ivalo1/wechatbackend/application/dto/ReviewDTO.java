package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class ReviewDTO {
    @NotNull private Long orderItemId;
    @NotNull @Min(1) @Max(5) private Integer rating;
    private String content;
    private String images;
}
