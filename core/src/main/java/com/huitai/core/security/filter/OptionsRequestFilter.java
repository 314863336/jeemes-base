package com.huitai.core.security.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 跨域options请求拦截器，只返回header <br>
 * date: 2020/4/23 14:56 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class OptionsRequestFilter extends OncePerRequestFilter {

    private String allowedMethods;

    public OptionsRequestFilter(String allowedMethods){
        this.allowedMethods = allowedMethods;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Methods", allowedMethods);
            response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            return;
        }
        filterChain.doFilter(request, response);
    }
}
