package com.huitai.core.system.utils;

import com.huitai.core.global.SystemConstant;
import com.huitai.common.utils.GenerateUUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * description: CaptchaUtil <br>
 * date: 2020/4/27 8:45 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Component
public class CaptchaUtil {
    private final String SPLIT = ":";
    private final int CAPTCHA_EXPIRED = 60; // 60秒有效期

    @Autowired
    protected RedisTemplate<String, Serializable> redisCacheTemplate;

    /**
     * description: 添加验证码 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 9:01 <br>
     * author: XJM <br>
     */
    public String addCaptcha(String captcha){
        String key = GenerateUUIDUtil.uuid();
        redisCacheTemplate.opsForValue().set(SystemConstant.REDIS_USER_CAPTCHA_KEY + SPLIT + key, captcha);
        redisCacheTemplate.expire(SystemConstant.REDIS_USER_CAPTCHA_KEY + SPLIT + key, CAPTCHA_EXPIRED, TimeUnit.SECONDS);
        return key;
    }

    /**
     * description: 验证验证码 key:前端传参验证存储在redis的key <br>
     * version: 1.0 <br>
     * date: 2020/4/27 8:58 <br>
     * author: XJM <br>
     */
    public String getCaptcha(String key){
        boolean isTimeout = redisCacheTemplate.hasKey(SystemConstant.REDIS_USER_CAPTCHA_KEY + SPLIT + key);
        if(!isTimeout){
            return null;
        }
        // redis中的验证码
        String token = (String)redisCacheTemplate.opsForValue().get(SystemConstant.REDIS_USER_CAPTCHA_KEY + SPLIT + key);

        return token;
    }

    /**
     * description: 验证验证码 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 9:02 <br>
     * author: XJM <br>
     */
    public boolean checkCaptcha(String redisCaptcha, String captcha){
        if(redisCaptcha.toUpperCase().equals(captcha.toUpperCase())){
            return true;
        }else{
            return false;
        }
    }
}
