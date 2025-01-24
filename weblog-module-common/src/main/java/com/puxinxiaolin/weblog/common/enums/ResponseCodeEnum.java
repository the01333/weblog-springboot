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
    // ----------- 参数异常状态码 -----------
    PARAM_NOT_VALID("10001", "参数错误"),


    // ----------- 业务异常状态码 -----------
    LOGIN_FAIL("20000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    FORBIDDEN("20004", "演示账号仅支持查询操作！"),
    USERNAME_NOT_FOUND("20003", "该用户不存在"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
    TAG_CANT_DUPLICATE("20006", "请勿添加表中已存在的标签！"),
    TAG_NOT_EXISTED("20007", "该标签不存在！"),
    FILE_UPLOAD_FAILED("20008", "文件上传失败！"),
    ARTICLE_NOT_EXISTED("20009", "该文章不存在！"),
    CATEGORY_NOT_EXISTED("20010", "提交的分类不存在！")
    ;

    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

}
