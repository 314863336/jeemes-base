package com.huitai.core.security.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * description: 自定义密码校验 <br>
 * date: 2020/4/23 13:54 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

    /**
     * description: 对数据库查出的密码处理 charSequence 数据库查出的密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 13:57 <br>
     * author: XJM <br>
     */
    @Override
    public String encode(CharSequence charSequence) {
        return super.encode(charSequence);
    }

    /**
     * description: 密码校验 rawPassword 登录页面传来的密码 password 数据库密码 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 13:56 <br>
     * author: XJM <br>
     */
    @Override
    public boolean matches(CharSequence rawPassword, String password) {
        return BCrypt.checkpw(rawPassword.toString(), password);
    }
}
