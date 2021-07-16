package com.huitai.core.security.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.utils.CustomAuthentication;
import com.huitai.core.system.entity.HtSysUser;
import com.huitai.core.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * description: 认证拦截器 <br>
 * date: 2020/4/23 14:28 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class AuthenticationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    private AuthenticationManager authenticationManager;


    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
    private RequestMatcher[] requestMatchers;

    public AuthenticationFilter(String[] ignorings) {
        requestMatchers = new AntPathRequestMatcher[ignorings.length];
        for (int i = 0; i < ignorings.length; i++) {
            requestMatchers[i] = new AntPathRequestMatcher(ignorings[i]);
        }
    }

    protected String getToken(HttpServletRequest request) {
        String key = request.getHeader(SecurityConst.AUTH_TOKEN_KEY);
        if (StringUtils.isBlank(key)) {
            key = request.getParameter(SecurityConst.AUTH_TOKEN_KEY);
        }
        return key;
    }

    protected boolean isIgnorings(HttpServletRequest request){
        for (int i = 0; i < this.requestMatchers.length; i++) {
            if(this.requestMatchers[i].matches(request)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authResult = null;
        AuthenticationException failed = null;
        String key = null;
        if(!isIgnorings(request)) {
            try {
                key = getToken(request);
                if (StringUtils.isBlank(key)) {
                    logger.error("认证失败, token传值为空");
                    throw new InternalAuthenticationServiceException("认证失败");
                } else {
                    CustomAuthentication authToken = new CustomAuthentication(key);
                    authResult = this.getAuthenticationManager().authenticate(authToken);
                }
            } catch (InternalAuthenticationServiceException e) {
                logger.error("拦截器异常，内部异常", e);
                failed = e;
            } catch (AuthenticationException e) {
                logger.error("拦截器异常，认证异常", e);
                failed = e;
            }
            if (authResult != null) {
                successfulAuthentication(request, response, filterChain, authResult);
            } else {
                unsuccessfulAuthentication(request, response, failed);
                return;
            }
            HtSysUser curUser = UserUtil.getCurUser();
            MDC.put("loginCode", curUser.getLoginCode());
            MDC.put("token", key);
        }

        filterChain.doFilter(request, response);
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setAuthenticationSuccessHandler(
            AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(
            AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }
}
