package com.puxinxiaolin.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Mybatis-Plus 配置类
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Configuration
@MapperScan(basePackages = {"com.puxinxiaolin.weblog.common.domain.mapper"})
public class MybatisPlusConfig {

}
