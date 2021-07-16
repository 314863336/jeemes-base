package com.huitai.core.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

/**
 * description: BaseController <br>
 * date: 2020/4/8 17:18 <br>
 * author: PLF <br>
 * version: 1.0 <br>
 */
public abstract class BaseController {
    @Autowired
    protected MessageSource messageSource;
}
