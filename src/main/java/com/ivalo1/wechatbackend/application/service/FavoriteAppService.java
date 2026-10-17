package com.ivalo1.wechatbackend.application.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.FavoriteMapper;
import com.ivalo1.wechatbackend.domain.product.Favorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class FavoriteAppService {
    private final FavoriteMapper favoriteMapper;
    public List<Favorite> getFavorites(Long userId) {
        return favoriteMapper.selectList(new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId).orderByDesc(Favorite::getCreateTime));
    }
    public void addFavorite(Long userId, Long productId) {
        Favorite f = new Favorite(); f.setUserId(userId); f.setProductId(productId); f.setCreateTime(LocalDateTime.now());
        favoriteMapper.insert(f);
    }
    public void removeFavorite(Long userId, Long productId) {
        favoriteMapper.delete(new LambdaQueryWrapper<Favorite>().eq(Favorite::getUserId, userId).eq(Favorite::getProductId, productId));
    }
}
