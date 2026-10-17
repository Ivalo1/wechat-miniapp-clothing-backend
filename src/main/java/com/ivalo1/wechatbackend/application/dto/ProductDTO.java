package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal; import java.util.List;
@Data
public class ProductDTO {
    @NotBlank private String name; private String description; private BigDecimal price;
    private String mainImage; private String images; private Long categoryId;
    private Integer status; private Integer sortOrder;
    private List<SkuDTO> skus; private List<String> tags;
    @Data public static class SkuDTO { private Long id; private String skuName; private String color; private String size; private BigDecimal price; private Integer stock; private String image; private Integer status; }
}
