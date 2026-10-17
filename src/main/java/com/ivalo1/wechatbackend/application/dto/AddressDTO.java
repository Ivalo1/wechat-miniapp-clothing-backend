package com.ivalo1.wechatbackend.application.dto;
import jakarta.validation.constraints.NotBlank; import lombok.Data;
@Data
public class AddressDTO {
    @NotBlank private String receiverName; @NotBlank private String receiverPhone;
    @NotBlank private String province; @NotBlank private String city;
    @NotBlank private String district; @NotBlank private String detailAddress;
    private Integer isDefault;
}
