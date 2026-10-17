package com.ivalo1.wechatbackend.application.service;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ivalo1.wechatbackend.application.dto.WxLoginDTO;
import com.ivalo1.wechatbackend.application.dto.AdminLoginDTO;
import com.ivalo1.wechatbackend.application.vo.LoginVO;
import com.ivalo1.wechatbackend.common.BusinessException;
import com.ivalo1.wechatbackend.common.ErrorCode;
import com.ivalo1.wechatbackend.domain.auth.AdminUser;
import com.ivalo1.wechatbackend.domain.auth.AdminUserRepository;
import com.ivalo1.wechatbackend.domain.member.User;
import com.ivalo1.wechatbackend.domain.member.UserRepository;
import com.ivalo1.wechatbackend.infrastructure.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class AuthAppService {
    private final WxMaService wxMaService;
    private final UserRepository userRepository;
    private final AdminUserRepository adminUserRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginVO wxLogin(WxLoginDTO dto) {
        try {
            WxMaJscode2SessionResult result = wxMaService.getUserService().getSessionInfo(dto.getCode());
            String openid = result.getOpenid();
            User user = userRepository.findByOpenid(openid);
            if (user == null) {
                user = new User();
                user.setOpenid(openid);
                user.setUnionid(result.getUnionid());
                user.setPoints(0);
                user.setCreateTime(LocalDateTime.now());
                user.setUpdateTime(LocalDateTime.now());
                userRepository.save(user);
            }
            LoginVO vo = new LoginVO();
            vo.setToken(jwtUtil.generateUserToken(user.getId(), openid));
            vo.setNickname(user.getNickname());
            vo.setAvatar(user.getAvatar());
            return vo;
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.WX_LOGIN_FAIL);
        }
    }

    public LoginVO adminLogin(AdminLoginDTO dto) {
        AdminUser admin = adminUserRepository.findByUsername(dto.getUsername());
        if (admin == null || !passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            throw new BusinessException(ErrorCode.ADMIN_LOGIN_FAIL);
        }
        LoginVO vo = new LoginVO();
        vo.setToken(jwtUtil.generateAdminToken(admin.getId(), admin.getUsername(), admin.getRoleCode()));
        return vo;
    }
}
