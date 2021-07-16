package com.huitai.core.security.config;

import com.alibaba.fastjson.JSONObject;
import com.huitai.common.model.Result;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.filter.AuthenticationFilter;
import com.huitai.core.security.utils.CustomAuthentication;
import com.huitai.core.security.utils.SecurityCache;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description: 身份认证拦截配置 <br>
 * date: 2020/4/23 13:48 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class AuthFilterConfig<T extends AuthFilterConfig<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {

    private AuthenticationFilter authFilter;
    private SecurityCache securityCache;

    public AuthFilterConfig(SecurityCache securityCache, String[] ignorings) {
        this.authFilter = new AuthenticationFilter(ignorings);
        this.securityCache = securityCache;
    }

    @Override
    public void configure(B http) {
        authFilter.setAuthenticationManager(((HttpSecurity)http).getSharedObject(AuthenticationManager.class));
        tokenHandler();

        AuthenticationFilter filter = postProcess(authFilter);
        // 设置拦截器顺序 设置默认的身份认证拦截器顺序
        ((HttpSecurity)http).addFilterAt(filter, BasicAuthenticationFilter.class);
    }

    public AuthFilterConfig<T, B> tokenHandler() {
        authFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                CustomAuthentication token = ((CustomAuthentication) authentication);
                securityCache.refreshToken(token.getKey());
            }
        });
        // 身份验证失败处理
        authFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                MessageSource messageSource = SpringContextUtil.getBean("messageSource");
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                PrintWriter out = response.getWriter();
                out.write(JSONObject.toJSONString(Result.error(SecurityConst.NOT_LOGIN_ERROR_CODE,messageSource.getMessage("system.error.userAuthFailed", null, LocaleContextHolder.getLocale()))));
                out.flush();
                out.close();
            }
        });
        return this;
    }
}
