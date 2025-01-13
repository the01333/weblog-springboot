package com.puxinxiaolin.weblog.web.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: User 实体类
 * @author: YCcLin
 * @date: 2025/1/13
 **/
@Data
public class User {

    @NotNull(message = "用户名不能为空")
    private String username;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于等于 18")
    @Max(value = 100, message = "年龄必须小于等于 100")
    private Integer age;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

}
