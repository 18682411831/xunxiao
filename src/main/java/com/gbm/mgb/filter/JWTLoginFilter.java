package com.gbm.mgb.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbm.mgb.core.Result;
import com.gbm.mgb.core.ResultCode;
import com.gbm.mgb.core.ResultGenerator;
import com.gbm.mgb.dto.AccountCredentials;
import com.gbm.mgb.helper.TokenAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Waylon on 2017/9/27.
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    /**
     * 登录时需要验证时候调用
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        // JSON反序列化成 AccountCredentials
        //AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(StringUtils.isEmpty(username.trim())||StringUtils.isEmpty(password.trim())){
            throw new ServletException("账户名或密码为空");
        }
        AccountCredentials creds = new AccountCredentials(username,password);
        // 返回一个验证令牌
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword()
                )
        );
    }

    /**
     * 验证成功后调用
     * @param req
     * @param res
     * @param chain
     * @param auth
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        TokenAuthenticationService.createAuthentication(res, auth.getName());
    }

    /**
     * 验证失败后调用
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Result result = new Result();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        result.setCode(ResultCode.UNAUTHORIZED).setMessage("签名认证失败");
        response.getOutputStream().println(JSON.toJSONString(result));
    }

}
