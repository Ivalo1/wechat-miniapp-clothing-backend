package com.ivalo1.wechatbackend.domain.auth;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true) @TableName("t_admin_role")
public class AdminRole extends BaseEntity {
    private String roleCode;
    private String roleName;
    private String permissions; // JSON array of permission codes
    private Integer status;
}
