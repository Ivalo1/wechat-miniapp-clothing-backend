package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.math.BigDecimal; import java.util.List;
@Data
public class ProductDetailVO { private Long id; private String name; private String description; private BigDecimal price; private String mainImage; private List<String> images; private Long categoryId; private Integer sales; private Integer status; private List<SkuVO> skus; private List<String> tags;
    @Data public static class SkuVO { private Long id; private String skuName; private String color; private String size; private BigDecimal price; private Integer stock; private String image; }
}
