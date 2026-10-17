package com.ivalo1.wechatbackend.application.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivalo1.wechatbackend.application.dto.ProductDTO;
import com.ivalo1.wechatbackend.application.vo.ProductDetailVO;
import com.ivalo1.wechatbackend.application.vo.ProductListVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.product.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductSkuMapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ProductAppService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductSkuMapper productSkuMapper;
    private final ProductTagMapper productTagMapper;

    public PageResult<ProductListVO> getProductList(int page, int size, Long categoryId, String keyword) {
        Page<Product> p = new Page<>(page, size);
        LambdaQueryWrapper<Product> w = new LambdaQueryWrapper<Product>().eq(Product::getStatus, 1);
        if (categoryId != null) w.eq(Product::getCategoryId, categoryId);
        if (keyword != null && !keyword.isBlank()) w.like(Product::getName, keyword);
        w.orderByDesc(Product::getCreateTime);
        Page<Product> result = productMapper.selectPage(p, w);
        List<ProductListVO> list = result.getRecords().stream().map(this::toListVO).collect(Collectors.toList());
        return new PageResult<>(list, result.getTotal(), page, size);
    }

    @Cacheable(value = "product", key = "'detail:' + #id")
    public ProductDetailVO getProductDetail(Long id) {
        Product product = productRepository.findById(id);
        if (product == null) throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        return toDetailVO(product);
    }

    @Transactional
    @CacheEvict(value = "product", allEntries = true)
    public Long saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName()); product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice()); product.setMainImage(dto.getMainImage());
        product.setImages(dto.getImages()); product.setCategoryId(dto.getCategoryId());
        product.setStatus(dto.getStatus() != null ? dto.getStatus() : 0);
        product.setSortOrder(dto.getSortOrder()); product.setSales(0);
        product.setCreateTime(LocalDateTime.now()); product.setUpdateTime(LocalDateTime.now());
        productRepository.save(product);
        if (dto.getSkus() != null) {
            for (ProductDTO.SkuDTO s : dto.getSkus()) {
                ProductSku sku = new ProductSku();
                sku.setProductId(product.getId()); sku.setSkuName(s.getSkuName());
                sku.setColor(s.getColor()); sku.setSize(s.getSize());
                sku.setPrice(s.getPrice()); sku.setStock(s.getStock());
                sku.setImage(s.getImage()); sku.setStatus(s.getStatus() != null ? s.getStatus() : 1);
                sku.setCreateTime(LocalDateTime.now()); sku.setUpdateTime(LocalDateTime.now());
                productSkuMapper.insert(sku);
            }
        }
        return product.getId();
    }

    private ProductListVO toListVO(Product p) {
        ProductListVO vo = new ProductListVO();
        vo.setId(p.getId()); vo.setName(p.getName()); vo.setPrice(p.getPrice());
        vo.setMainImage(p.getMainImage()); vo.setSales(p.getSales()); vo.setStatus(p.getStatus());
        return vo;
    }

    private ProductDetailVO toDetailVO(Product p) {
        ProductDetailVO vo = new ProductDetailVO();
        vo.setId(p.getId()); vo.setName(p.getName()); vo.setDescription(p.getDescription());
        vo.setPrice(p.getPrice()); vo.setMainImage(p.getMainImage()); vo.setCategoryId(p.getCategoryId());
        vo.setSales(p.getSales()); vo.setStatus(p.getStatus());
        List<ProductSku> skus = productSkuMapper.selectList(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, p.getId()));
        vo.setSkus(skus.stream().map(s -> {
            ProductDetailVO.SkuVO sv = new ProductDetailVO.SkuVO();
            sv.setId(s.getId()); sv.setSkuName(s.getSkuName()); sv.setColor(s.getColor());
            sv.setSize(s.getSize()); sv.setPrice(s.getPrice()); sv.setStock(s.getStock()); sv.setImage(s.getImage());
            return sv;
        }).collect(Collectors.toList()));
        return vo;
    }
}
