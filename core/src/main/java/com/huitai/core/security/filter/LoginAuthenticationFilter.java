package com.huitai.core.security.filter;

import com.huitai.common.utils.IpUtil;
import com.huitai.common.utils.SpringContextUtil;
import com.huitai.common.utils.StringUtil;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.exception.CustomUsernameNotFoundException;
import com.huitai.core.security.utils.LoginForm;
import com.huitai.core.system.utils.CaptchaUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.huitai.core.security.consts.SecurityConst.LOGIN_FORM_SESSION_KEY;

/**
 * description: 登录拦截器 <br>
 * date: 2020/4/23 14:41 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    public static final long ERROR_0 = 0;
    public static final long ERROR_1 = 1;
    public static final long ERROR_2 = 2;
    public static final long ERROR_3 = 3;
    private CaptchaUtil captchaUtil;

    public LoginAuthenticationFilter(CaptchaUtil captchaUtil) {
        // 只拦截/login
        super(new AntPathRequestMatcher("/login", "POST"));
        this.captchaUtil = captchaUtil;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = null;
        String password = null;
        String captcha = null;
        String captchaKey = null;
        LoginForm loginForm = new LoginForm(request);
        loginForm.setIp(IpUtil.getRemoteAddr(request));
        request.getSession().setAttribute(LOGIN_FORM_SESSION_KEY, loginForm);
        username = loginForm.getUsername();
        password = loginForm.getPassword();
        captcha = loginForm.getCaptcha();
        captchaKey = loginForm.getCaptchaKey();
        if(!StringUtil.isEmpty(captchaKey)){
            MessageSource messageSource = SpringContextUtil.getBean("messageSource");
            if(StringUtil.isEmpty(captcha)){
                throw new CustomUsernameNotFoundException(SecurityConst.CAPTCHA_ISNULL_ERROR_CODE, messageSource.getMessage("system.error.loginVerificationCodeIsNull", null, LocaleContextHolder.getLocale()));
            }else{
                String redisCaptcha = captchaUtil.getCaptcha(captchaKey);
                if(redisCaptcha == null){
                    throw new CustomUsernameNotFoundException(SecurityConst.CAPTCHA_TIMEOUT_ERROR_CODE, messageSource.getMessage("system.error.loginVerificationCodeExpired", null, LocaleContextHolder.getLocale()));
                }else{
                    boolean checked = captchaUtil.checkCaptcha(redisCaptcha, captcha);
                    if(!checked){
                        throw new CustomUsernameNotFoundException(SecurityConst.CAPTCHA_IS_WRONG_ERROR_CODE, messageSource.getMessage("system.error.loginVerificationCodeIsWrong", null, LocaleContextHolder.getLocale()));
                    }
                }
            }
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
