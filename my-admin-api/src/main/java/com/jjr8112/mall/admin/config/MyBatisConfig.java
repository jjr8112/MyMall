package com.jjr8112.mall.admin.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis相关配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.jjr8112.mall.mbg.mapper","com.jjr8112.mall.admin.dao"})
public class MyBatisConfig {
}

