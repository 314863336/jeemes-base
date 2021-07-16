package com.huitai.core.security.service;

import com.huitai.core.security.utils.CustomAuthentication;
import com.huitai.core.security.utils.Token;
import com.huitai.core.security.utils.CustomUser;
import com.huitai.core.security.utils.SecurityCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * description: springsecurity认证中心，AuthenticationFilter过滤器会在这里认证 <br>
 * date: 2020/4/23 15:01 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class CustomAuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    private SecurityCache securityCache;

    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(SecurityCache securityCache, PasswordEncoder passwordEncoder) {
        this.securityCache = securityCache;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        CustomAuthentication token = ((CustomAuthentication)authentication);

        String key = token.getKey();
        Token customToken = securityCache.getToken(key);
        if(customToken == null){
            logger.error("认证失败, securityCache用户为空，key:" + key);
            throw new InternalAuthenticationServiceException("认证失败");
        }

        CustomUser userDetails = (CustomUser) CustomUser.customBuilder().username(customToken.getHtSysUser().getLoginCode()).password(passwordEncoder.encode(customToken.getHtSysUser().getPassword())).roles(customToken.getPermissions()).build();
        userDetails.setToken(customToken);
        token = new CustomAuthentication(userDetails, key, userDetails.getAuthorities());
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(CustomAuthentication.class);
    }
}
