package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.ivalo1.wechatbackend.domain.product.Product;
import com.ivalo1.wechatbackend.domain.product.ProductRepository;
import com.ivalo1.wechatbackend.domain.product.ProductSku;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductSkuMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;

    @Override
    public Product findById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        LambdaQueryWrapper<Product> w = new LambdaQueryWrapper<>();
        if (categoryId != null) w.eq(Product::getCategoryId, categoryId);
        w.eq(Product::getStatus, 1).orderByDesc(Product::getCreateTime);
        return productMapper.selectList(w);
    }

    @Override
    public void save(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void update(Product product) {
        productMapper.updateById(product);
    }

    @Override
    public ProductSku findSkuById(Long skuId) {
        return productSkuMapper.selectById(skuId);
    }
}
