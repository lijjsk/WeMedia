package com.lijjsk.utils.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    //密钥
    private static String secret="dasadfjksfgsegfgsjdaf";
    /**
     * 生成token
     */
    public static String creatToken(Map<String,Object> map){
        String token = Jwts.builder()
                //输入信息
                .setClaims(map)
                //颁发时间
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                //加密算法
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return token;

    }
    /**
     * token解析用户信息
     */
    public static Claims parseToken(String token){
        //使用相同密钥
        Claims claims=Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
