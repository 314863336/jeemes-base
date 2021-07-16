package com.huitai.core.exception;

import com.huitai.core.base.BaseException;

/**
 * description: DemoException <br>
 * date: 2020/4/8 16:34 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public class SystemException extends BaseException {
    public SystemException(){
        super();
    }
    public SystemException(String message){
        super(message);
    }
}
