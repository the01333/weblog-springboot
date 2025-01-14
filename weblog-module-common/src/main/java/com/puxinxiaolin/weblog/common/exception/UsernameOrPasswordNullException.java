package com.puxinxiaolin.weblog.common.exception;

import javax.security.sasl.AuthenticationException;

/**
 * @description: 用户名或密码为空异常
 * @author: YCcLin
 * @date: 2025/1/14
 **/
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }

}
