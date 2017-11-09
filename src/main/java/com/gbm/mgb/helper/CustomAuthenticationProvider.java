package com.gbm.mgb.helper;

import com.gbm.mgb.domain.rbac.User;
import com.gbm.mgb.dto.GrantedAuthorityImpl;
import com.gbm.mgb.service.rbac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * 自定义身份认证验证组件
 * Created by Waylon on 2017/9/27.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取认证的用户名 & 密码
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        System.out.println(userService);
        // TODO 认证逻辑(结合实际业务做改善)
        User user = userService.doLogin(name,password);

        if (user != null) {
            // 这里设置权限和角色

            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add( new GrantedAuthorityImpl("ROLE_ADMIN") );
            authorities.add( new GrantedAuthorityImpl("AUTH_WRITE") );

            // 生成令牌
            Authentication auth = new UsernamePasswordAuthenticationToken(user.getId(), user.getEmail(), authorities);


            return auth;
        }else {
            throw new BadCredentialsException("密码错误~");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
