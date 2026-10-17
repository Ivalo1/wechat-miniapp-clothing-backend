package com.ivalo1.wechatbackend.domain.cart;

public interface CartRepository {
    java.util.List<CartItem> findByUserId(Long userId);
    CartItem findById(Long id);
    void save(CartItem cartItem);
    void update(CartItem cartItem);
    void deleteById(Long id);
    void deleteByIds(java.util.List<Long> ids);
}
