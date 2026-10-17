package com.ivalo1.wechatbackend.service;

import com.ivalo1.wechatbackend.mapper.AddressMapper;
import com.ivalo1.wechatbackend.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressMapper addressMapper;

    public AddressService(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public List<Address> listByUser(Long userId) {
        return addressMapper.findByUserId(userId);
    }

    public void create(Address address) {
        addressMapper.insert(address);
    }
}
