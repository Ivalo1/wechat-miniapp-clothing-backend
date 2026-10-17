package com.ivalo1.wechatbackend.application.vo;
import lombok.Data;
import java.util.List;
@Data
public class CategoryTreeVO { private Long id; private String name; private String icon; private Integer level; private List<CategoryTreeVO> children; }
