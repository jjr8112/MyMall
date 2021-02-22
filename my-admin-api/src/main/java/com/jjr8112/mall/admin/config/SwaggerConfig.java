package com.jjr8112.mall.admin.config;

import com.jjr8112.mall.common.config.BaseSwaggerConfig;
import com.jjr8112.mall.common.swagger.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.jjr8112.mall.admin.controller")
                .title("mall后台系统")
                .description("mall后台相关接口文档")
                .contactName("jjr8112")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}
