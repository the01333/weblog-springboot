package com.puxinxiaolin.weblog.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @description: User 实体类
 * @author: YCcLin
 * @date: 2025/1/13
 **/
@Data
@ApiModel(value = "用户实体类")
public class User {

    // 用户名
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    // 性别
    @ApiModelProperty(value = "性别")
    @NotNull(message = "性别不能为空")
    private Integer gender;

    // 年龄
    @ApiModelProperty(value = "年龄")
    @NotNull(message = "年龄不能为空")
    @Min(value = 18, message = "年龄必须大于等于 18")
    @Max(value = 100, message = "年龄必须小于等于 100")
    private Integer age;

    // 邮箱
    @ApiModelProperty(value = "邮箱")
    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    // 创建时间
    private LocalDateTime createTime;

    // 更新日期
    private LocalDate updateDate;

    // 时间
    private LocalTime time;

}
