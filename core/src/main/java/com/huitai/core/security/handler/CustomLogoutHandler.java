package com.huitai.core.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.huitai.common.model.Result;
import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.utils.SecurityCache;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * description: CustomLogoutHandler <br>
 * date: 2020/4/23 15:00 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class CustomLogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {
    private SecurityCache securityCache;

    public CustomLogoutHandler(SecurityCache securityCache){
        this.securityCache = securityCache;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String key = getToken(request);
        // 清除securityCache中的登陆令牌
        if (StringUtils.isNotBlank(key)) {
            securityCache.delToken(key);
        }
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.write(JSONObject.toJSONString(Result.ok("登出成功")));
        out.flush();
        out.close();
    }

    protected String getToken(HttpServletRequest request) {
        String key = request.getHeader(SecurityConst.AUTH_TOKEN_KEY);
        if (StringUtils.isBlank(key)) {
            key = request.getParameter(SecurityConst.AUTH_TOKEN_KEY);
        }
        return key;
    }
}
