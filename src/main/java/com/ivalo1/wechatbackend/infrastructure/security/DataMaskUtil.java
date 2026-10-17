package com.ivalo1.wechatbackend.infrastructure.security;

/**
 * 敏感数据脱敏工具类
 */
public class DataMaskUtil {

    /** 手机号脱敏: 138****1234 */
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }

    /** 身份证脱敏: 110***********1234 */
    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 8) return idCard;
        return idCard.substring(0, 3) + "***********" + idCard.substring(idCard.length() - 4);
    }

    /** 姓名脱敏: 张* */
    public static String maskName(String name) {
        if (name == null || name.length() <= 1) return name;
        return name.charAt(0) + "*".repeat(name.length() - 1);
    }

    /** 邮箱脱敏: a***@example.com */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        int atIdx = email.indexOf("@");
        if (atIdx <= 1) return "*" + email.substring(atIdx);
        return email.charAt(0) + "***" + email.substring(atIdx);
    }

    /** OpenID 脱敏: 前6后4 */
    public static String maskOpenId(String openId) {
        if (openId == null || openId.length() < 10) return openId;
        return openId.substring(0, 6) + "****" + openId.substring(openId.length() - 4);
    }
}
