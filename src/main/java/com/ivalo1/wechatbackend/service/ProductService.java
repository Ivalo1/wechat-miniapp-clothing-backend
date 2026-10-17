package com.ivalo1.wechatbackend.service;

import com.ivalo1.wechatbackend.mapper.ProductMapper;
import com.ivalo1.wechatbackend.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public List<Product> listProducts() {
        return productMapper.findAll();
    }

    public Product getProduct(Long id) {
        return productMapper.findById(id);
    }
}
