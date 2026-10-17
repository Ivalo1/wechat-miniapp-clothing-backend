package com.ivalo1.wechatbackend.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ivalo1.wechatbackend.domain.address.Address;
import com.ivalo1.wechatbackend.domain.address.AddressRepository;
import com.ivalo1.wechatbackend.infrastructure.persistence.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {

    private final AddressMapper addressMapper;

    @Override
    public Address findById(Long id) {
        return addressMapper.selectById(id);
    }

    @Override
    public List<Address> findByUserId(Long userId) {
        return addressMapper.selectList(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, userId).orderByDesc(Address::getIsDefault));
    }

    @Override
    public void save(Address address) {
        addressMapper.insert(address);
    }

    @Override
    public void update(Address address) {
        addressMapper.updateById(address);
    }

    @Override
    public void deleteById(Long id) {
        addressMapper.deleteById(id);
    }
}
