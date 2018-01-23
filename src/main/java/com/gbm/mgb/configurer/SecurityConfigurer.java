package com.gbm.mgb.configurer;

import com.gbm.mgb.filter.JWTAuthenticationFilter;
import com.gbm.mgb.filter.JWTLoginFilter;
import com.gbm.mgb.helper.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.annotation.Resource;

/**
 * spring security实现
 * @author waylon
 * @date 2017/11/02
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends  WebSecurityConfigurerAdapter {


    @Bean
    public CustomAuthenticationProvider getCustomAuthenticationProvider(){
        return new CustomAuthenticationProvider();
    }

    /**
    * 设置 HTTP 验证规则
    */
    @Override
    @ExceptionHandler
    protected void configure(HttpSecurity http) throws Exception {
        //关闭csrf验证
        http.csrf().disable()
                //对请求进行认证
                .authorizeRequests()
                //所有/请求都放行
                .antMatchers("/").permitAll()
                // 所有 /login 的POST请求 都放行
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers(HttpMethod.GET,"/login").permitAll()
                .antMatchers(HttpMethod.POST,"/order/pay/notify").permitAll()
                .antMatchers(HttpMethod.POST,"/sms/send").permitAll()
                .antMatchers(
                        "/",
                        "/v2/api-docs",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/admin/*").permitAll()
                // 所有请求需要身份认证
               .anyRequest().authenticated().and()
                // 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来处理 这个类处理所有的JWT相关内容
                .addFilterBefore(new JWTLoginFilter("/login",authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // 添加一个过滤器验证其他请求的Token是否合法
                .addFilterBefore(new JWTAuthenticationFilter(),UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(getCustomAuthenticationProvider());
    }

    


}
