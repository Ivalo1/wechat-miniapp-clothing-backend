package com.ivalo1.wechatbackend.infrastructure.persistence.repository;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.review.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository @RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {
    private final ProductReviewMapper reviewMapper;
    @Override public List<ProductReview> findByProductId(Long productId, int page, int size) { return reviewMapper.selectList(new LambdaQueryWrapper<ProductReview>().eq(ProductReview::getProductId, productId).orderByDesc(ProductReview::getCreateTime).last("LIMIT " + size + " OFFSET " + (page-1)*size)); }
    @Override public boolean hasReviewed(Long userId, Long orderItemId) { return reviewMapper.selectCount(new LambdaQueryWrapper<ProductReview>().eq(ProductReview::getUserId, userId).eq(ProductReview::getOrderItemId, orderItemId)) > 0; }
    @Override public void save(ProductReview review) { reviewMapper.insert(review); }
}
