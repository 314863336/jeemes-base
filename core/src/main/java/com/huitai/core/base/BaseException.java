package com.huitai.core.base;

/**
 * description: BaseException <br>
 * date: 2020/4/8 14:49 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public class BaseException extends RuntimeException {

    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
