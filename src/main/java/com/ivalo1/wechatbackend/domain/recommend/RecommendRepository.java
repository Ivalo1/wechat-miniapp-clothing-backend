package com.ivalo1.wechatbackend.domain.recommend;
import java.util.List;
public interface RecommendRepository {
    void saveBehavior(UserBehavior behavior);
    List<Long> findFavoriteCategoryIds(Long userId);
    List<Long> findRecentViewedProductIds(Long userId, int limit);
}
