package com.ivalo1.wechatbackend.domain.review;
import java.util.List;
public interface ReviewRepository {
    List<ProductReview> findByProductId(Long productId, int page, int size);
    boolean hasReviewed(Long userId, Long orderItemId);
    void save(ProductReview review);
}
