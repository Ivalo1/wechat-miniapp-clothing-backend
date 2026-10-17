package com.ivalo1.wechatbackend.model;

import lombok.Data;

@Data
public class Address {
    private Long id;
    private Long userId;
    private String consignee;
    private String phone;
    private String fullAddress;
    private Boolean isDefault;
}
