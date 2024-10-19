package com.xiao.notify.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis config
 *
 * @author lh
 */
@Configuration
@MapperScan("com.xiao.notify.mapper")
public class MyBatisConfig {

}
