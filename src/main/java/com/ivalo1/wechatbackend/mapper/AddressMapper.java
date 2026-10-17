package com.ivalo1.wechatbackend.mapper;

import com.ivalo1.wechatbackend.model.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    List<Address> findByUserId(Long userId);
    int insert(Address address);
}
