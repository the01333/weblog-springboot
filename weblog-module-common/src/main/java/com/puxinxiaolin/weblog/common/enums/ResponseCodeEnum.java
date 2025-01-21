package com.puxinxiaolin.weblog.common.enums;

import com.puxinxiaolin.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 响应异常状态码枚举类
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中..."),

    // ----------- 业务异常状态码 -----------
    PRODUCT_NOT_FOUND("20000", "商品不存在（测试使用）"),

    // ----------- 参数异常状态码 -----------
    PARAM_NOT_VALID("10001", "参数错误"),

    // ----------- 登录异常状态码 -----------
    LOGIN_FAIL("20000", "登录失败"),

    // ----------- 用户密码登录异常状态码 -----------
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),

    // ----------- 权限异常状态码 -----------
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),

    // ----------- 演示账号异常状态码 -----------
    FORBIDDEN("20004", "演示账号仅支持查询操作！"),

    // ----------- 用户不存在异常状态码 -----------
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    ;

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

}
