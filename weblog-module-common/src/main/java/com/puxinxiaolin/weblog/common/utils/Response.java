package com.puxinxiaolin.weblog.common.utils;

import com.puxinxiaolin.weblog.common.exception.BaseExceptionInterface;
import com.puxinxiaolin.weblog.common.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 响应参数工具类
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Data
public class Response<T> implements Serializable {

    // 是否成功
    private boolean success = true;
    // 异常码
    private String errorCode;
    // 响应信息
    private String message;
    // 响应数据
    private T data;

    // =================================== 成功响应 ===================================
    public static <T> Response<T> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    // =================================== 失败响应 ===================================
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String errorCode) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        return response;
    }

    public static <T> Response<T> fail(String errorCode, String errorMessage) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(errorCode);
        response.setMessage(errorMessage);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(bizException.getErrorCode());
        response.setMessage(bizException.getErrorMessage());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setErrorCode(baseExceptionInterface.getErrorCode());
        response.setMessage(baseExceptionInterface.getErrorMessage());
        return response;
    }


}
