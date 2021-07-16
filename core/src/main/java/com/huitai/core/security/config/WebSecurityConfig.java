package com.huitai.core.security.config;

import com.huitai.core.security.consts.SecurityConst;
import com.huitai.core.security.filter.OptionsRequestFilter;
import com.huitai.core.security.handler.CustomLogoutHandler;
import com.huitai.core.security.service.CustomAuthenticationProvider;
import com.huitai.core.security.service.CustomUserDetailsService;
import com.huitai.core.security.utils.CustomPasswordEncoder;
import com.huitai.core.security.utils.SecurityCache;
import com.huitai.core.system.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * description: springsecurity主要配置 <br>
 * date: 2020/4/23 13:40 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@EnableGlobalMethodSecurity(prePostEnabled =true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityCache securityCache;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Value("${security.web.ignorings}")
    private String[] ignorings;

    @Value("${security.cors.allowedOrigins}")
    private String[] allowedOrigins;

    @Value("${security.cors.allowedMethods}")
    private String[] allowedMethods;

    @Value("${security.cors.allowedMethods}")
    private String allowedMethodS;

    @Value("${security.cors.allowedHeaders}")
    private String[] allowedHeaders;

    @Value("${security.cors.path}")
    private String[] corsPath;

    @Value("${security.cors.allowed}")
    private boolean corsAllowed;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(this.ignorings).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable() // CRSF禁用
                .formLogin().disable() // 禁用form登录
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //禁用session
                .and()// 添加header设置，支持ajax请求
                .headers().defaultsDisabled().cacheControl()// 禁用掉默认添加的header，只留下cache-control header
                .and()
                .addHeaderWriter(new StaticHeadersWriter(Arrays.asList( // 暴露header中的token
                        new Header("Access-Control-Expose-Headers", SecurityConst.AUTH_TOKEN_KEY))))
                .and()// 拦截OPTIONS请求，直接返回header
                .addFilterAfter(new OptionsRequestFilter(allowedMethodS), CorsFilter.class)
                // 添加登录filter
                .apply((new LoginFilterConfig<>(securityCache, captchaUtil)))
                .and()
                // 添加token的filter
                .apply(new AuthFilterConfig<>(securityCache, this.ignorings))
                .and()
                // 使用默认的logoutFilter, 地址为logout
                .logout()
                .addLogoutHandler(tokenClearLogoutHandler());
        if(corsAllowed){
            http.cors();
        }

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider()).authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CustomPasswordEncoder customPasswordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    protected AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider(securityCache, customPasswordEncoder());
    }

    @Bean
    protected AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService());
        daoProvider.setPasswordEncoder(customPasswordEncoder());
        daoProvider.setHideUserNotFoundExceptions(false);
        return daoProvider;
    }

    @Override
    protected CustomUserDetailsService userDetailsService() {
        return securityCache.userDetailsService();
    }

    protected CustomLogoutHandler tokenClearLogoutHandler() {
        return new CustomLogoutHandler(securityCache);
    }

    /**
     * description: 跨域配置 <br>
     * version: 1.0 <br>
     * date: 2020/4/23 15:08 <br>
     * author: XJM <br>
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.length == 0 ? new String[]{"*"} : allowedOrigins));
        configuration.setAllowedMethods(Arrays.asList(allowedMethods.length == 0 ? new String[]{"*"} : allowedMethods));
        configuration.setAllowedHeaders(Arrays.asList(allowedHeaders.length == 0 ? new String[]{"*"} : allowedHeaders));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        for (String path : corsPath) {
            source.registerCorsConfiguration(path, configuration);
        }
        return source;
    }

}
