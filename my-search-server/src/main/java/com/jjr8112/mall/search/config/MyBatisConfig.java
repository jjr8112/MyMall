package com.jjr8112.mall.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis相关配置
 */
@Configuration
@MapperScan({"com.jjr8112.mall.mbg.mapper","com.jjr8112.mall.search.dao"})
public class MyBatisConfig {
}
