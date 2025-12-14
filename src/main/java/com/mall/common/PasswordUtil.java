package com.mall.common;

import org.springframework.util.DigestUtils;
import java.nio.charset.StandardCharsets;

public class PasswordUtil {

    // 定义一个固定的盐值（Salt），混在密码里，防止简单的彩虹表破解
    private static final String SALT = "Mall_Project_Secret_Key_@#123";

    /**
     * 生成加密密码
     * 算法：MD5( 用户输入密码 + 固定盐值 )
     */
    public static String encrypt(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        String base = rawPassword + SALT;
        return DigestUtils.md5DigestAsHex(base.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 校验密码
     * @param inputPassword 用户输入的明文密码
     * @param dbPassword 数据库里存的加密密码
     */
    public static boolean match(String inputPassword, String dbPassword) {
        if (inputPassword == null || dbPassword == null) {
            return false;
        }
        String encryptedInput = encrypt(inputPassword);
        return encryptedInput.equals(dbPassword);
    }
}