package com.ivalo1.wechatbackend.application.service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.application.dto.CartAddDTO;
import com.ivalo1.wechatbackend.application.dto.CartUpdateDTO;
import com.ivalo1.wechatbackend.application.vo.CartVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.cart.*;
import com.ivalo1.wechatbackend.domain.product.*;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.ProductSkuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List; import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CartAppService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductSkuMapper productSkuMapper;

    public List<CartVO> getCartList(Long userId) {
        return cartRepository.findByUserId(userId).stream().map(this::toVO).collect(Collectors.toList());
    }

    public void addToCart(Long userId, CartAddDTO dto) {
        ProductSku sku = productSkuMapper.selectById(dto.getSkuId());
        if (sku == null) throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        CartItem existing = cartRepository.findByUserId(userId).stream()
                .filter(c -> c.getSkuId().equals(dto.getSkuId())).findFirst().orElse(null);
        if (existing != null) {
            existing.updateQuantity(existing.getQuantity() + dto.getQuantity());
            cartRepository.update(existing);
        } else {
            CartItem item = new CartItem();
            item.setUserId(userId); item.setProductId(sku.getProductId());
            item.setSkuId(dto.getSkuId()); item.setQuantity(dto.getQuantity());
            item.setSelected(1); item.setCreateTime(LocalDateTime.now()); item.setUpdateTime(LocalDateTime.now());
            cartRepository.save(item);
        }
    }

    public void updateCartItem(Long userId, CartUpdateDTO dto) {
        CartItem item = cartRepository.findById(dto.getCartItemId());
        if (item == null || !item.getUserId().equals(userId)) throw new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND);
        item.updateQuantity(dto.getQuantity());
        cartRepository.update(item);
    }

    public void removeFromCart(Long userId, Long cartItemId) {
        CartItem item = cartRepository.findById(cartItemId);
        if (item == null || !item.getUserId().equals(userId)) throw new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND);
        cartRepository.deleteById(cartItemId);
    }

    private CartVO toVO(CartItem ci) {
        CartVO vo = new CartVO();
        vo.setId(ci.getId()); vo.setProductId(ci.getProductId()); vo.setSkuId(ci.getSkuId());
        vo.setQuantity(ci.getQuantity()); vo.setSelected(ci.getSelected());
        ProductSku sku = productSkuMapper.selectById(ci.getSkuId());
        if (sku != null) { vo.setProductName(sku.getSkuName()); vo.setSkuName(sku.getSkuName()); vo.setPrice(sku.getPrice()); vo.setProductImage(sku.getImage()); }
        return vo;
    }
}
