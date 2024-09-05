package com.example.business.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtils {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;
    private static final int MAX_LENGTH = 255; // 设置最大长度为 255

    /**
     * 生成随机盐值
     * @return 盐值的Base64字符串
     */
    private static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密密码并限制长度
     * @param password 原始密码
     * @return 带有盐值和哈希值的加密字符串，且长度不超过255字符
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = generateSalt();
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = factory.generateSecret(spec).getEncoded();
        String encodedHash = Base64.getEncoder().encodeToString(hash);

        // 组合盐值和哈希值
        String combined = salt + ":" + encodedHash;

        // 如果组合后的长度超过255，进行截断
        if (combined.length() > MAX_LENGTH) {
            combined = combined.substring(0, MAX_LENGTH);
        }

        return combined;
    }

    /**
     * 验证密码
     * @param originalPassword 原始密码
     * @param storedPassword 存储的加密密码字符串
     * @return 如果密码匹配返回true，否则返回false
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static boolean verifyPassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        String salt = parts[0];
        String hash = parts[1];

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), Base64.getDecoder().decode(salt), ITERATIONS, KEY_LENGTH);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] generatedHash = factory.generateSecret(spec).getEncoded();
        String encodedGeneratedHash = Base64.getEncoder().encodeToString(generatedHash);

        // 对比时需要考虑可能的截断，故将比较的长度限制为存储哈希的长度
        return hash.equals(encodedGeneratedHash.substring(0, Math.min(hash.length(), encodedGeneratedHash.length())));
    }
}
