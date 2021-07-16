package com.huitai.core.security.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * description: CustomUsernameNotFoundException <br>
 * date: 2020/4/27 9:36 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class CustomUsernameNotFoundException extends UsernameNotFoundException {

    private int code;

    public CustomUsernameNotFoundException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomUsernameNotFoundException(String msg) {
        super(msg);
    }

    public CustomUsernameNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
