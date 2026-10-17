package com.ivalo1.wechatbackend.infrastructure.persistence.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivalo1.wechatbackend.domain.address.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<Address> {
}
