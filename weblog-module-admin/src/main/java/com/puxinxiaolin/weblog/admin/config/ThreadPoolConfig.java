package com.puxinxiaolin.weblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @description: 自定义线程池
 * @author: YCcLin
 * @date: 2025/2/11
 **/
@Configuration
@EnableAsync    // 启用异步特性, 任何使用 @Async 注解的方法都会通过该线程池被异步执行
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(17);  // 核心线程数（推荐: CPU核心数 + 1）
        executor.setMaxPoolSize(32);  // 最大线程数（推荐: CPU核心数 * 2）
        executor.setQueueCapacity(100);  // 队列容量
        executor.setThreadNamePrefix("WeblogThreadPool-");  // 线程名前缀
        executor.initialize();
        return executor;
    }

}
