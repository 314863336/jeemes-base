package com.huitai.core.system.controller;

import com.huitai.common.model.Result;
import com.huitai.common.utils.IpUtil;
import com.huitai.core.security.utils.SecurityCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * description: LoginController <br>
 * date: 2020/4/26 14:54 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@RestController
@Api(value = "登录接口")
public class LoginController {

    @Autowired
    protected SecurityCache securityCache;  //redis操作 存储对象

    @ApiOperation(value = "验证ip白名单",notes="验证ip白名单")
    @RequestMapping(value = "/checkIp", method = {RequestMethod.GET})
    public Result checkIp(HttpServletRequest request) {
        String ip = IpUtil.getRemoteAddr(request);
        Result result = null;

        // 获取所有ip白名单,及已经成功登陆过的ip
        if(securityCache.checkWhiteIp(ip)){
            result =  Result.ok(true);
        }else{
            result =  Result.ok(false);
        }
        return result;
    }
}
