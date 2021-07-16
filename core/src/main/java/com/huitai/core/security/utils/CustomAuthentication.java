package com.huitai.core.security.utils;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * description: 自定义springsecurity 认证token <br>
 * date: 2020/4/23 14:25 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
public class CustomAuthentication extends AbstractAuthenticationToken {
    private UserDetails principal;
    private String credentials;
    private String key;

    public CustomAuthentication(String key){
        super(new ArrayList<>());
        this.key = key;
    }

    public CustomAuthentication(UserDetails principal, String key, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.key = key;
    }

    @Override
    public void setDetails(Object details) {
        super.setDetails(details);
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getKey() {
        return key;
    }
}
