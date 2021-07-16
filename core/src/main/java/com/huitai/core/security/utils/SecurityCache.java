package com.huitai.core.security.utils;

import com.huitai.core.global.SystemConstant;
import com.huitai.common.utils.GenerateUUIDUtil;
import com.huitai.common.utils.IpUtil;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.service.CustomUserDetailsService;
import com.huitai.core.system.service.HtSysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * description: 用户安全存储工具 <br>
 * date: 2020/4/23 14:19 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Service
public class SecurityCache {

    @Autowired
    protected RedisTemplate<String, Serializable> redisCacheTemplate;  //redis操作 存储对象

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private HtSysConfigService htSysConfigService;

    @Value("${security.token.expired}")
    private long tokenExpired;

    private final String SPLIT = ":";

    // ip白名单过期时间, 天
    private final int whiteIpExpired = 30;

    // 密码错误过期时间，小时
    private final int loginErrorExpired = 12;

    private final String failNumForCaptchaConfigKey = "sys.login.failNumForCaptcha";


    /**
     * description: 获取userDetailsService <br>
     * version: 1.0 <br>
     * date: 2020/4/23 16:11 <br>
     * author: XJM <br>
     */
    public CustomUserDetailsService userDetailsService() {
        return userDetailsService;
    }

    /**
     * description: 设置token值 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:44 <br>
     * author: XJM <br>
     */
    public void setToken(String key, Token token){
        redisCacheTemplate.opsForValue().set(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key, token);
        redisCacheTemplate.expire(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key, tokenExpired, TimeUnit.SECONDS);
    }

    /**
     * description: 添加token <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:44 <br>
     * author: XJM <br>
     */
    public String addToken(Token token){
        String uuid = GenerateUUIDUtil.uuid();
        setToken(uuid, token);
        return uuid;
    }

    /**
     * description: 获取token <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:45 <br>
     * author: XJM <br>
     */
    public Token getToken(String key){
        boolean isExists = redisCacheTemplate.hasKey(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key);
        if(!isExists){
            return null;
        }
        Token token = (Token)redisCacheTemplate.opsForValue().get(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key);
        return token;
    }

    /**
     * description: 删除token <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:45 <br>
     * author: XJM <br>
     */
    public void delToken(String key){
        Token token = getToken(key);
        if(token != null){
            redisCacheTemplate.delete(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key);
        }
    }

    /**
     * description: 判断是否需要刷新token，如果需要则刷新 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 16:00 <br>
     * author: XJM <br>
     */
    public void refreshToken(String key){
        long remainSeconds = redisCacheTemplate.getExpire(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key, TimeUnit.SECONDS);
        if(remainSeconds < SecurityConst.AUTH_TOKEN_REFRESH){
            redisCacheTemplate.expire(SystemConstant.REDIS_USER_LOGIN_KEY + SPLIT + key, tokenExpired, TimeUnit.SECONDS);
        }
    }

    /**
     * description: 添加白名单ip, ip当作key <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:00 <br>
     * author: XJM <br>
     */
    public void addWhiteIp(String ip){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
           return;
        }
        redisCacheTemplate.opsForValue().set(SystemConstant.REDIS_USER_IP_KEY + SPLIT + ip, ip);
        redisCacheTemplate.expire(SystemConstant.REDIS_USER_IP_KEY + SPLIT + ip, whiteIpExpired, TimeUnit.DAYS);
    }

    /**
     * description: 去除白名单ip, ip当作key <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:00 <br>
     * author: XJM <br>
     */
    public void removeWhiteIp(String ip){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return;
        }
        boolean isExists = checkWhiteIp(ip);
        if(isExists){
            redisCacheTemplate.delete(SystemConstant.REDIS_USER_IP_KEY + SPLIT + ip);
        }
    }

    /**
     * description: 判断是否存在白名单ip, ip当作key <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:07 <br>
     * author: XJM <br>
     */
    public boolean checkWhiteIp(String ip){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return true;
        }
        boolean isExists = redisCacheTemplate.hasKey(SystemConstant.REDIS_USER_IP_KEY + SPLIT + ip);
        return isExists;
    }

    /**
     * description: 获取密码错误 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:00 <br>
     * author: XJM <br>
     */
    public void addLoginError(String ip, String username){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return;
        }
        if(!checkLoginError(ip, username)){
            redisCacheTemplate.opsForValue().set(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username, 1);
        }else{
            int time = (int)redisCacheTemplate.opsForValue().get(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username);
            redisCacheTemplate.opsForValue().set(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username, time + 1);
        }

        redisCacheTemplate.expire(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username, loginErrorExpired, TimeUnit.HOURS);
    }

    /**
     * description: 获取密码错误失败次数 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:48 <br>
     * author: XJM <br>
     */
    public int getLoginErrorTime(String ip, String username){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return 0;
        }
        if(!checkLoginError(ip, username)){
            return 0;
        }else{
            int time = (int)redisCacheTemplate.opsForValue().get(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username);
            return time;
        }
    }

    /**
     * description: 去除是否存在密码错误对应ip和用户名 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:00 <br>
     * author: XJM <br>
     */
    public void removeLoginError(String ip, String username){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return;
        }
        boolean isExists = checkLoginError(ip, username);
        if(isExists){
            redisCacheTemplate.delete(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username);
        }
    }

    /**
     * description: 判断是否存在密码错误对应ip和用户名 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:07 <br>
     * author: XJM <br>
     */
    public boolean checkLoginError(String ip, String username){
        // 本地ip不操作
        if(IpUtil.isLocalAddr(ip)){
            return false;
        }
        boolean isExists = redisCacheTemplate.hasKey(SystemConstant.REDIS_USER_PASSWORD_ERROR_KEY + SPLIT + ip + SPLIT + username);
        return isExists;
    }

    /**
     * description: 获取参数，密码失败多少次显示验证码 <br>
     * version: 1.0 <br>
     * date: 2020/4/27 10:32 <br>
     * author: XJM <br>
     */
    public int getFailNumForCaptcha(){
        String value = htSysConfigService.getValueByKey(failNumForCaptchaConfigKey);
        return Integer.parseInt(value);
    }
}
