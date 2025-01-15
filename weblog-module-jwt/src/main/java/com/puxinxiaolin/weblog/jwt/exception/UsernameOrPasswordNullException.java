package com.puxinxiaolin.weblog.jwt.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * @description: 用户名或密码为空异常
 * @author: YCcLin
 * @date: 2025/1/14
 **/
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String message) {
        super(message);
    }

    public UsernameOrPasswordNullException(String message, Throwable cause) {
        super(message, cause);
    }

}
