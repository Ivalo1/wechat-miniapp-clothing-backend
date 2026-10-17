package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.*; import lombok.Data;
@Data
public class CartUpdateDTO { @NotNull private Long cartItemId; @NotNull @Min(1) private Integer quantity; }
