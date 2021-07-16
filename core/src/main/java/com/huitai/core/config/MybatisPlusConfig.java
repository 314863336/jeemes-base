package com.huitai.core.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: MybatisPlusConfig <br>
 * date: 2020/4/9 15:34 <br>
 * author: XJM <br>
 * version: 1.0 <br>
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * description: mybatis分页 <br>
     * version: 1.0 <br>
     * date: 2020/4/9 15:35 <br>
     * author: XJM <br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
