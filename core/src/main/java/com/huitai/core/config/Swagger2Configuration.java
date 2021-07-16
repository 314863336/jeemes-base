package com.huitai.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * description: swagger2配置类
 * date: 2020/4/8 9:55
 * author: TYJ
 * version: 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    //@Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("系统模块")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huitai.core.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    //@Bean
    public Docket createRestApi2(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("对接.NET平台")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huitai.lamp.remote.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createRestApi3(){

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("对恒嘉开发接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.huitai.lamp.remote.controller.pub"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder().title("山东慧泰智能科技")
                .description("接口文档")
                .contact(new Contact("jeemes",null,"18914104664@163.com"))
                .version("1.0")
                .build();
    }
}
