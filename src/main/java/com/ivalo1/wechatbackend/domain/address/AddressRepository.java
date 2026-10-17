package com.ivalo1.wechatbackend.domain.address;

public interface AddressRepository {
    Address findById(Long id);
    java.util.List<Address> findByUserId(Long userId);
    void save(Address address);
    void update(Address address);
    void deleteById(Long id);
}
