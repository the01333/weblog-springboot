package com.puxinxiaolin.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * @description: JWT 操作工具类
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Component
public class JwtTokenHelper implements InitializingBean {

    // 签发人
    @Value(value = "${jwt.issuer}")
    private String issuer;

    // 密钥
    private Key key;

    // JWT 解析
    private JwtParser jwtParser;

    // Token 失效时间（分钟）
    @Value(value = "${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    /**
     * 解码配置文件中配置的 Base64 编码的 secret 为秘钥
     *
     * @param base64Key
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 初始化 JwtParser
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 考虑到不同服务器之间可能存在时钟偏移，setAllowedClockSkewSeconds 用于设置能够容忍的最大的时钟误差
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**
     * 生成 Token
     *
     * @param username 用户名
     * @return
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        // Token 失效时间
        LocalDateTime expireTime = now.plusHours(tokenExpireTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuer(issuer).signWith(key)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .compact();
    }

    /**
     * 解析 Token
     *
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 校验 Token 是否可用
     *
     * @param token
     * @return
     */
    public void validateToken(String token) {
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 解析 Token 获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameByToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成一个 Base64 的安全秘钥
     *
     * @return
     */
    private static String generateBase64Key() {
        // 生成安全秘钥
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        // 将密钥进行 Base64 编码
        return Base64.getEncoder()
                .encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
}
