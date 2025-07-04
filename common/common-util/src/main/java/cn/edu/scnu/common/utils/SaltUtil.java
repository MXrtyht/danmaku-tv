package cn.edu.scnu.common.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtil {
    // 生成一个安全的随机盐，默认长度 16 字节
    public static String generateSalt(int byteLength) {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[byteLength];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    // 默认 16 字节盐值（128位）
    public static String generateSalt() {
        return generateSalt(16);
    }
}