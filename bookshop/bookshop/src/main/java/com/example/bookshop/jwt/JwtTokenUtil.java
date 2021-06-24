package com.example.bookshop.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {


    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "create";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负载生成JWT的token
     *
     * @param claims 负载
     * @return String
     * @author huangzifan
     * @since 2020-10-28 9:13
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())  //判断令牌有没有失效
                .signWith(SignatureAlgorithm.HS512, secret) //secret 加密时需要的密钥
                .compact();
    }

    /**
     * 从token中获取JWT的负载
     *
     * @param token
     * @return Claims
     * @author huangzifan
     * @since 2020-10-28 9:16
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret) //解密需要 加密时的密钥
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("jwt格式验证失效");
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     *
     * @return Date
     * @author huangzifan
     * @since 2020-10-28 9:18
     */
    public Date generateExpirationDate() {  //生成token的过期时间
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取登录用户名
     *
     * @param token 客户端传入的token
     * @return String
     * @author huangzifan
     * @since 2020-10-28 9:21
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库查询出的用户信息
     * @return boolean
     * @author huangzifan
     * @since 2020-10-28 9:22
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     *
     * @param token
     * @return boolean
     * @author huangzifan
     * @since 2020-10-28 9:23
     */
    public boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     *
     * @param token 客户端传入的token
     * @return java.util.Date
     * @author huangzifan
     * @since 2020-10-28 9:23
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     *
     * @param userDetails 从数据库查询出的用户信息
     * @return String
     * @author huangzifan
     * @since 2020-10-28 9:24
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token
     * @return boolean
     * @author huangzifan
     * @since 2020-10-28 9:25
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     *
     * @param token
     * @return java.lang.String
     * @author huangzifan
     * @since 2020-10-28 9:25
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

}
