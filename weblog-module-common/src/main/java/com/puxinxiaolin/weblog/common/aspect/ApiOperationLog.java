package com.puxinxiaolin.weblog.common.aspect;

import java.lang.annotation.*;

/**
 * @description: 用于记录方法执行时的操作日志的自定义注解
 * @author: YCcLin
 * @date: 2025/1/13
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {

    /**
     * API 功能描述
     *
     * @return
     */
    String description() default "";

}
