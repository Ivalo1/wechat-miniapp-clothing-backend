package com.ivalo1.wechatbackend.domain.auth;
import com.ivalo1.wechatbackend.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @EqualsAndHashCode(callSuper = true) @TableName("t_admin_user")
public class AdminUser extends BaseEntity {
    private String username;
    private String password;
    private String realName;
    private String phone;
    private String roleCode;
    private Integer status; // 0禁用 1启用
}
