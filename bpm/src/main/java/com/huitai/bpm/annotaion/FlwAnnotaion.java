package com.huitai.bpm.annotaion;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: 实体类对应的业务名称 <br>
 * @date 2020-12-04 17:13 <br>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface FlwAnnotaion {
    String value();
}
