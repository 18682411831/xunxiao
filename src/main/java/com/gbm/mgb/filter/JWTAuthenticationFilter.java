package com.gbm.mgb.filter;

import com.gbm.mgb.helper.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 拦截所有需要JWT的请求做JWT验证
 * Created by Waylon on 2017/9/27.
 */
public class JWTAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {
        Authentication authentication = TokenAuthenticationService
                .getAuthentication((HttpServletRequest)request);
        String url = ((HttpServletRequest) request).getRequestURL()+"";
        if (url.contains("http://DESKTOP-SNUCUQN:8080/admin")){
            filterChain.doFilter(request,response);
        }else {
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

}
