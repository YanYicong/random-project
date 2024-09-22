package com.example.utils.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT Token工具类
 */

public class JwtUtils {

//    密钥
    private static String SECRET_KEY = "bqcxgshnzhhddswlzgmmwmfczdjbaqlgwsbzzxdgnnpwgcnpwllpwlbjs";

//    username临时存储
    public static String USERNAME = "";

    /**
     * 创建JWT
     * @param claims
     * @param subject
     * @return
     */
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
//                设置声明信息
                .setClaims(claims)
//                设置主题，通常是用户名
                .setSubject(subject)
//                设置token签发时间
                .setIssuedAt(new Date(System.currentTimeMillis()))
//                设置token过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
//                使用HS256算法和密钥进行签名
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET_KEY)
//                生成最终token字符串
                .compact();
    }

    /**
     * 获取token
     * @param username
     * @return
     */
    public static String generateToken(String username) {
        USERNAME = username;
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * 验证token
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public static String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * 提取所有token信息
     * @param token
     * @return
     */
    public static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否过期
     * @param token
     * @return
     */
    private static Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 从token中提取过期时间
     * @param token
     * @return
     */
    private static Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

}
