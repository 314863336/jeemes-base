package com.huitai.core.security.consts;

/**
 * description: 相关常量配置 <br>
 * version: 1.0 <br>
 * date: 2020/4/23 14:29 <br>
 * author: XJM <br>
 */
public class SecurityConst {

    // 登录参数用户名
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";

    // 登录参数密码
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    // 登录参数验证码
    public static final String SPRING_SECURITY_FORM_CAPTCHA_KEY = "captcha";

    // 登录参数验证码key
    public static final String SPRING_SECURITY_FORM_CAPTCHAKEY_KEY = "captchaKey";

    // 登录令牌刷新时间剩余 单位 s
    public static final long AUTH_TOKEN_REFRESH = 300;

    // 登录令牌名称
    public static final String AUTH_TOKEN_KEY = "Authorization";

    // 登录信息存储在session中的key
    public static final String LOGIN_FORM_SESSION_KEY = "LOGIN_FORM_SESSION_KEY";

    // 未登录返回code
    public static final int NOT_LOGIN_ERROR_CODE = 401;

    // 用户名不存在返回code
    public static final int USER_NAME_NOT_FOUND_ERROR_CODE = 101;

    // 用户禁用返回code
    public static final int USER_DISABLE_ERROR_CODE = 102;

    // 验证码为空
    public static final int CAPTCHA_ISNULL_ERROR_CODE = 103;

    // 验证码过期
    public static final int CAPTCHA_TIMEOUT_ERROR_CODE = 104;

    // 验证码不正确
    public static final int CAPTCHA_IS_WRONG_ERROR_CODE = 105;

    // 密码错误返回code
    public static final int PASSWORD_NOT_CORRECT_ERROR_CODE = 111;

    // 密码错误超次数返回code
    public static final int PASSWORD_NOT_CORRECT_ERROR_TIMES_CODE = 112;

}
