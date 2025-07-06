package cn.edu.scnu.danmakutv.common.authentication;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;

public class JwtHelper {

    private static final long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    private static final String tokenSignKey = "M6JUZD5dVVOS7bZb4wLSciECukkunIns2WW7klTxA1EE90KhfC0ihQE3LsRS4Zmf" +
            "AF2o5LJeOMHFvjb3apVdkAkEpGsqcMy5q54qSXe3QkVSnhTeISQdlCy750yZdSAf" +
            "jJwPO0TN26RN4kLgOotIWiSq9deLm8I0ryuqgNzZG8LUZDwGkFJrAuHaSUOw4emI" +
            "ndotYFrk4ql3jnxsDwhyv7HRKyRsdlKzAvaWAhSlj3gpKiWEgp0078SNmh1pUpN1" +
            "D4duAP0XDQ3iHfqdgLHhfI2kXFYozHpEr7g9IVSzDzhFGJqYuGxxsL7gFJZOP2oF";
    private static final Key key = Keys.hmacShaKeyFor(tokenSignKey.getBytes());

    /**
     * 生成 JWT，载荷只包含 userId
     */
    public static String createToken (Long userId) {
        return Jwts.builder()
                   .setSubject("danmaku-USER")
                   .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                   .claim("userId", userId)
                   .signWith(key, SignatureAlgorithm.HS512)
                   .compressWith(CompressionCodecs.GZIP)
                   .compact();
    }

    /**
     * 从 JWT 中解析出 userId
     */
    public static Long getUserId (String token) {
        if (!StringUtils.hasText(token)) return -1L;

        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(key)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

            Number id = claims.get("userId", Number.class);
            return id == null ? -1L : id.longValue();
        } catch (JwtException e) {
            // token 格式非法、过期等
            return -1L;
        }
    }

    /**
     * JWT 是无状态的，客户端删除即可
     */
    public static void removeToken (String token) {
        // no-op
    }

    // 测试
    public static void main (String[] args) {
        String token = createToken(123L);
        System.out.println("Token: " + token);
        System.out.println("User ID: " + getUserId(token));
    }
}
