package com.huitai.core.system.controller;

import com.huitai.common.model.Result;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.system.utils.CaptchaUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description: CaptchaController <br>
 * date: 2020/4/8 15:21 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@RestController
@Api(value = "验证码接口")
public class CaptchaController {

    @Autowired
    protected CaptchaUtil captchaUtil;

    @ApiOperation(value = "获取验证码",notes="获取验证码")
    @RequestMapping("/captcha")
    public Result captcha() {
        Captcha captcha = new ArithmeticCaptcha();
        Result result = Result.ok();
        result.put("data", captcha.toBase64());
        String key = captchaUtil.addCaptcha(captcha.text().toUpperCase());
        result.put(SecurityConst.SPRING_SECURITY_FORM_CAPTCHAKEY_KEY, key);
        return result;
    }
}
