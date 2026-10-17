package com.ivalo1.wechatbackend.application.service;
import com.ivalo1.wechatbackend.application.dto.AddressDTO;
import com.ivalo1.wechatbackend.application.vo.AddressVO;
import com.ivalo1.wechatbackend.common.*;
import com.ivalo1.wechatbackend.domain.address.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List; import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class AddressAppService {
    private final AddressRepository addressRepository;

    public List<AddressVO> getAddressList(Long userId) {
        return addressRepository.findByUserId(userId).stream().map(this::toVO).collect(Collectors.toList());
    }

    public AddressVO getById(Long userId, Long id) {
        Address a = addressRepository.findById(id);
        if (a == null || !a.getUserId().equals(userId)) throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
        return toVO(a);
    }

    public Long saveAddress(Long userId, AddressDTO dto) {
        Address a = new Address();
        a.setUserId(userId); copyFromDto(a, dto);
        a.setCreateTime(LocalDateTime.now()); a.setUpdateTime(LocalDateTime.now());
        addressRepository.save(a);
        return a.getId();
    }

    public void updateAddress(Long userId, Long id, AddressDTO dto) {
        Address a = addressRepository.findById(id);
        if (a == null || !a.getUserId().equals(userId)) throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
        copyFromDto(a, dto); a.setUpdateTime(LocalDateTime.now());
        addressRepository.update(a);
    }

    public void deleteAddress(Long userId, Long id) {
        Address a = addressRepository.findById(id);
        if (a == null || !a.getUserId().equals(userId)) throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
        addressRepository.deleteById(id);
    }

    private void copyFromDto(Address a, AddressDTO dto) {
        a.setReceiverName(dto.getReceiverName()); a.setReceiverPhone(dto.getReceiverPhone());
        a.setProvince(dto.getProvince()); a.setCity(dto.getCity());
        a.setDistrict(dto.getDistrict()); a.setDetailAddress(dto.getDetailAddress());
        a.setIsDefault(dto.getIsDefault());
    }

    private AddressVO toVO(Address a) {
        AddressVO vo = new AddressVO();
        vo.setId(a.getId()); vo.setReceiverName(a.getReceiverName()); vo.setReceiverPhone(a.getReceiverPhone());
        vo.setProvince(a.getProvince()); vo.setCity(a.getCity()); vo.setDistrict(a.getDistrict());
        vo.setDetailAddress(a.getDetailAddress()); vo.setFullAddress(a.getFullAddress()); vo.setIsDefault(a.getIsDefault());
        return vo;
    }
}
