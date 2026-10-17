package com.ivalo1.wechatbackend.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivalo1.wechatbackend.domain.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {

    @Update("UPDATE t_product_sku SET stock = stock - #{quantity} WHERE id = #{skuId} AND stock >= #{quantity}")
    int deductStock(@Param("skuId") Long skuId, @Param("quantity") int quantity);

    @Update("UPDATE t_product_sku SET stock = stock + #{quantity} WHERE id = #{skuId}")
    int restoreStock(@Param("skuId") Long skuId, @Param("quantity") int quantity);
}
