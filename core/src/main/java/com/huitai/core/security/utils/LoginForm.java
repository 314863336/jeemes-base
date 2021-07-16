package com.huitai.core.security.utils;

import com.alibaba.fastjson.JSONObject;
import com.huitai.common.utils.ServletUtil;
import com.huitai.core.security.consts.SecurityConst;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

/**
 * description: LoginFormUtil <br>
 * date: 2020/4/27 10:34 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class LoginForm {
    private String username;
    private String password;
    private String captcha;
    private String captchaKey;
    private String ip;

    public LoginForm(){}

    public LoginForm(HttpServletRequest request){
        if(request.getContentType().indexOf(MediaType.APPLICATION_JSON_VALUE) >= 0){
            JSONObject json = ServletUtil.getRequestPayload(request);
            username = json.get(SecurityConst.SPRING_SECURITY_FORM_USERNAME_KEY) == null ? null : json.get(SecurityConst.SPRING_SECURITY_FORM_USERNAME_KEY).toString();
            password = json.get(SecurityConst.SPRING_SECURITY_FORM_PASSWORD_KEY) == null ? null : json.get(SecurityConst.SPRING_SECURITY_FORM_PASSWORD_KEY).toString();
            captcha = json.get(SecurityConst.SPRING_SECURITY_FORM_CAPTCHA_KEY) == null ? null : json.get(SecurityConst.SPRING_SECURITY_FORM_CAPTCHA_KEY).toString();
            captchaKey = json.get(SecurityConst.SPRING_SECURITY_FORM_CAPTCHAKEY_KEY) == null ? null : json.get(SecurityConst.SPRING_SECURITY_FORM_CAPTCHAKEY_KEY).toString();
        }else{
            username = request.getParameter(SecurityConst.SPRING_SECURITY_FORM_USERNAME_KEY);
            password = request.getParameter(SecurityConst.SPRING_SECURITY_FORM_PASSWORD_KEY);
            captcha = request.getParameter(SecurityConst.SPRING_SECURITY_FORM_CAPTCHA_KEY);
            captchaKey = request.getParameter(SecurityConst.SPRING_SECURITY_FORM_CAPTCHAKEY_KEY);
        }
        if (username == null)
            username = "";
        if (password == null)
            password = "";
        if (captcha == null)
            captcha = "";
        if (captchaKey == null)
            captchaKey = "";
        username = username.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
