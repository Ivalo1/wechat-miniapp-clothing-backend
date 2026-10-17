package com.ivalo1.wechatbackend.domain.address;

import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_address")
public class Address extends BaseEntity {
    private Long userId;
    private String receiverName;
    private String receiverPhone;
    private String province;
    private String city;
    private String district;
    private String detailAddress;
    private Integer isDefault;

    public String getFullAddress() {
        return province + city + district + detailAddress;
    }
}
