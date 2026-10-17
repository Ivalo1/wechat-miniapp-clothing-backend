package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.cart.CartItem;
import com.ivalo1.wechatbackend.domain.cart.CartRepository;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.CartItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

    private final CartItemMapper cartItemMapper;

    @Override
    public List<CartItem> findByUserId(Long userId) {
        return cartItemMapper.selectList(new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getUserId, userId).orderByDesc(CartItem::getCreateTime));
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemMapper.selectById(id);
    }

    @Override
    public void save(CartItem cartItem) {
        cartItemMapper.insert(cartItem);
    }

    @Override
    public void update(CartItem cartItem) {
        cartItemMapper.updateById(cartItem);
    }

    @Override
    public void deleteById(Long id) {
        cartItemMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<Long> ids) {
        cartItemMapper.deleteBatchIds(ids);
    }
}
