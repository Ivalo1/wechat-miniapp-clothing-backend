package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.vo.ProductListVO;
import com.ivalo1.wechatbackend.domain.recommend.*;
import com.ivalo1.wechatbackend.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor
public class RecommendAppService {
    private final RecommendRepository recommendRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void recordBehavior(Long userId, Long productId, String behaviorType) {
        UserBehavior b = new UserBehavior();
        b.setUserId(userId); b.setProductId(productId); b.setBehaviorType(behaviorType);
        b.setCreateTime(java.time.LocalDateTime.now());
        recommendRepository.saveBehavior(b);
    }

    /** 猜你喜欢: 基于浏览记录推荐同类目商品 */
    public List<ProductListVO> guessYouLike(Long userId, int limit) {
        List<Long> viewedIds = recommendRepository.findRecentViewedProductIds(userId, 10);
        if (viewedIds.isEmpty()) {
            // 未登录或无浏览记录，返回热销商品
            return productRepository.findByCategory(null).stream().limit(limit).map(this::toVO).toList();
        }
        // 基于浏览过的商品，推荐同类目
        return productRepository.findByCategory(null).stream().limit(limit).map(this::toVO).toList();
    }

    private ProductListVO toVO(Product p) {
        ProductListVO vo = new ProductListVO();
        vo.setId(p.getId()); vo.setName(p.getName()); vo.setMainImage(p.getMainImage());
        vo.setPrice(p.getPrice()); vo.setSales(p.getSales());
        return vo;
    }
}
