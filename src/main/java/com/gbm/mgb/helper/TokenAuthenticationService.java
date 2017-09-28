package com.gbm.mgb.helper;

import com.alibaba.fastjson.JSON;
import com.gbm.mgb.core.Result;
import com.gbm.mgb.core.ResultGenerator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 签名服务
 * Created by Waylon on 2017/9/27.
 */
@Component
public class TokenAuthenticationService {
    /**
     * token密钥
     */
    @Value("${jwt.secret}")
    private static String SECRET = "gbm2017";

    /**
     * token失效毫秒
     */
    @Value("${jwt.expire-millisecond}")
    private static Long EXPIRE_MILLISECOND = 300000l;


    /**
    * header中标识
    */
    private static final String AUTH_HEADER_NAME = "x-authorization";

    public static void createAuthentication(HttpServletResponse response, String username){
        // 生成JWT
        String JWT = Jwts.builder()

                // TODO 保存权限（角色）
                .claim("authorities", "ROLE_ADMIN,AUTH_WRITE")
                // 用户名写入标题
                .setSubject(username)
                // 有效期设置
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_MILLISECOND))
                // 签名设置
                        .signWith(SignatureAlgorithm.HS512, SECRET)
                        .compact();
        // 将 JWT 写入 body
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            Result result = new Result();
            response.getOutputStream().println(JSON.toJSONString(ResultGenerator.genSuccessResult(JWT)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // JWT验证方法
    public static Authentication getAuthentication(HttpServletRequest request) {
        // 从Header中拿到token
        String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null) {
            // 解析 Token
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET)
                    // 去掉 Bearer
                    .parseClaimsJws(token)
                    .getBody();

            // 拿用户名
            String user = claims.getSubject();

            // 得到 权限（角色）
            List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));

            // 返回验证令牌
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }

}
