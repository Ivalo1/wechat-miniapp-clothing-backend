package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotBlank; import lombok.Data;
@Data
public class CategoryDTO { @NotBlank private String name; private Long parentId; private Integer sortOrder; private String icon; private Integer status; }
