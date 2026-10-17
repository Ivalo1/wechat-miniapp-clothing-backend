package com.ivalo1.wechatbackend.domain.product;

/**
 * 商品仓储接口
 */
public interface ProductRepository {
    Product findById(Long id);
    java.util.List<Product> findByCategory(Long categoryId);
    void save(Product product);
    void update(Product product);
    ProductSku findSkuById(Long skuId);
}
