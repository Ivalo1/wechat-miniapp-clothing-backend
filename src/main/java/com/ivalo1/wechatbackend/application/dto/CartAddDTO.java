package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotNull; import lombok.Data;
@Data
public class CartAddDTO { @NotNull private Long skuId; @NotNull private Integer quantity; }
