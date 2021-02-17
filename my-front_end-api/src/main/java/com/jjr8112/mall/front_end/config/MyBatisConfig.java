package com.jjr8112.mall.front_end.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan({"com.jjr8112.mall.mbg.mapper","com.jjr8112.mall.front_end.dao"})
public class MyBatisConfig {
}
