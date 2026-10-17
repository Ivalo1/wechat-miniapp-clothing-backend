package com.ivalo1.wechatbackend.infrastructure.search;
import com.ivalo1.wechatbackend.application.vo.ProductListVO;
import com.ivalo1.wechatbackend.common.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 商品搜索服务
 * 当前使用 DB 查询，后续可切换为 Elasticsearch
 */
@Slf4j
@Service
public class ProductSearchService {
    public PageResult<ProductListVO> search(String keyword, Long categoryId, String color, String clothingSize,
                                             Double minPrice, Double maxPrice, int page, int size) {
        // TODO: 切换为 Elasticsearch
        // 当前由 ProductAppService 直接查 DB
        return new PageResult<>(List.of(), 0, page, size);
    }

    public void indexProduct(Long productId) {
        // TODO: 同步商品到 ES 索引
        log.info("同步商品到ES: productId={}", productId);
    }

    public void removeIndex(Long productId) {
        log.info("从ES移除商品: productId={}", productId);
    }
}
