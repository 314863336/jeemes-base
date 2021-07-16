package com.huitai.core.security.config;

import com.alibaba.fastjson.JSONObject;
import com.huitai.common.model.Result;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.exception.CustomUsernameNotFoundException;
import com.huitai.core.security.filter.LoginAuthenticationFilter;
import com.huitai.core.security.service.CustomUserDetailsService;
import com.huitai.core.security.utils.LoginForm;
import com.huitai.core.security.utils.SecurityCache;
import com.huitai.core.security.utils.Token;
import com.huitai.core.system.utils.CaptchaUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.huitai.core.security.consts.SecurityConst.LOGIN_FORM_SESSION_KEY;

/**
 * description: LoginFilterConfig <br>
 * date: 2020/4/23 14:47 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LoginFilterConfig<T extends LoginFilterConfig<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private LoginAuthenticationFilter authFilter;
    private CustomUserDetailsService userDetailsService;
    private SecurityCache securityCache;

    public LoginFilterConfig(SecurityCache securityCache, CaptchaUtil captchaUtil) {
        this.authFilter = new LoginAuthenticationFilter(captchaUtil);
        this.userDetailsService = securityCache.userDetailsService();
        this.securityCache = securityCache;
    }

    @Override
    public void configure(B http) throws Exception {
        authFilter.setAuthenticationManager(((HttpSecurity)http).getSharedObject(AuthenticationManager.class));
        // 配置成功和失败处理类
        loginHandler();

        LoginAuthenticationFilter filter = postProcess(authFilter);
        // 设置拦截器顺序, 设置默认的登录拦截器顺序
        ((HttpSecurity)http).addFilterAt(filter, UsernamePasswordAuthenticationFilter.class);
    }

    // 配置过滤器认证成功和失败处理器
    public LoginFilterConfig<T,B> loginHandler(){
        authFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler(){

            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
                Object obj = request.getSession().getAttribute(LOGIN_FORM_SESSION_KEY);
                LoginForm loginForm = (LoginForm)obj;
                String ip = loginForm.getIp();
                String username = loginForm.getUsername();
                // 登录成功，添加ip白名单
                if(!securityCache.checkWhiteIp(ip)){
                    securityCache.addWhiteIp(ip);
                }
                // 登录成功，删除对应的密码错误记录
                securityCache.removeLoginError(ip, username);
                request.getSession().removeAttribute(LOGIN_FORM_SESSION_KEY);
                Token token = userDetailsService.createToken((UserDetails)authentication.getPrincipal());
                String key = securityCache.addToken(token);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.OK.value());
                PrintWriter out = response.getWriter();
                Result result = Result.ok();
                result.put("token", key);
                out.write(JSONObject.toJSONString(result));
                out.flush();
                out.close();
            }
        });
        authFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {

            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                //登录失败处理方法
                if(e instanceof CustomUsernameNotFoundException){
                    PrintWriter out = response.getWriter();
                    out.write(JSONObject.toJSONString(Result.error(((CustomUsernameNotFoundException) e).getCode(), e.getMessage())));
                    out.flush();
                    out.close();
                }else if(e instanceof BadCredentialsException){
                    Object obj = request.getSession().getAttribute(LOGIN_FORM_SESSION_KEY);
                    LoginForm loginForm = (LoginForm)obj;
                    securityCache.addLoginError(loginForm.getIp(), loginForm.getUsername());
                    Result result = null;
                    int failNum = securityCache.getLoginErrorTime(loginForm.getIp(), loginForm.getUsername());
                    MessageSource messageSource = SpringContextUtil.getBean("messageSource");
                    // ip用户登录任何一个账号失败次数超过设定次数，删除白名单
                    if(failNum >= securityCache.getFailNumForCaptcha()){
                        securityCache.removeWhiteIp(loginForm.getIp());
                        result = Result.error(SecurityConst.PASSWORD_NOT_CORRECT_ERROR_TIMES_CODE, messageSource.getMessage("system.error.loginPasswordIsWrong", null, LocaleContextHolder.getLocale()));
                    }else{
                        result = Result.error(SecurityConst.PASSWORD_NOT_CORRECT_ERROR_CODE, messageSource.getMessage("system.error.loginPasswordIsWrong", null, LocaleContextHolder.getLocale()));
                    }
                    request.getSession().removeAttribute(LOGIN_FORM_SESSION_KEY);
                    PrintWriter out = response.getWriter();
                    out.write(JSONObject.toJSONString(result));
                    out.flush();
                    out.close();
                }
            }
        });
        return this;
    }
}
