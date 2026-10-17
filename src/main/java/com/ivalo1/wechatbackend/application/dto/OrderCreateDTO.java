package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
@Data
public class OrderCreateDTO {
    @NotNull(message = "收货地址不能为空") private Long addressId;
    private String remark; private Long couponId;
    private List<Long> cartItemIds;
    private List<SkuItem> directBuyItems;
    @Data public static class SkuItem { private Long skuId; private Integer quantity; }
}
