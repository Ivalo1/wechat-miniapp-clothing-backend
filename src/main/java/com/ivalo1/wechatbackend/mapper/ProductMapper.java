package com.ivalo1.wechatbackend.mapper;

import com.ivalo1.wechatbackend.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAll();
    Product findById(@Param("id") Long id);
    int insert(Product product);
}
