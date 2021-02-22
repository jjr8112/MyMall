package com.jjr8112.mall.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 应用启动入口
 */
@SpringBootApplication
//@ComponentScan(basePackages =  "com.jjr8112.mall.common" )
@ComponentScan(basePackages = {"com.jjr8112.mall.admin","com.jjr8112.mall.common"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
