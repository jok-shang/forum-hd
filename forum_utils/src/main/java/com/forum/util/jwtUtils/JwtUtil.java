package com.forum.util.jwtUtils;

import com.forum.model.pojo.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @auther 尚智江
 * @Date 2023/4/10 23:50
 */
public class JwtUtil {

    // TODO 有效期设置位七天
    private static long time = 1000*60*60*24*7;
//    private static long time = 1000*60*60*24;
    //TODO 签名信息解密用
    private static String signature = "admin";

    /**
     * 创建token
     * @return string
     */
    public String creatToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        return jwtBuilder
                //TODO header 头部信息
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //TODO payload 负载
                .claim("uid",user.getUid())
                .claim("username",user.getUsername())
                .claim("headImage",user.getHeadImage())
                //TODO 用户类型
                .claim("role","admin")
                // TODO 签名
                .setSubject("admin")
                // TODO 有效期
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                // TODO signature
                .signWith(SignatureAlgorithm.HS256,signature)
                // TODO 拼接
                .compact();
    }

    /**
     * 检查前端返回token
     * @param token token
     * @return boolean
     */
    public boolean checkToken(String token){
        if (token == null){
            return false;
        }
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
