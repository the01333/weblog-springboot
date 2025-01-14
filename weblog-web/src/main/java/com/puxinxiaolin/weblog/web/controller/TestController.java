package com.puxinxiaolin.weblog.web.controller;

import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @description: 测试 Controller
 * @author: YCcLin
 * @date: 2025/1/13
 **/
@RestController
@Slf4j
@Api(tags = "首页模块")
public class TestController {

    @ApiOperation(value = "测试接口4")
    @PostMapping("/test4")
    @ApiOperationLog(description = "测试全局异常接口")
    public Response test4(@RequestBody @Validated User user) {
        return Response.success();
    }

    @ApiOperation(value = "测试接口3")
    @PostMapping("/test3")
    @ApiOperationLog(description = "测试其他异常接口")
    public Response test3(@RequestBody @Validated User user,
                          BindingResult bindingResult) {
        int i = 1 / 0;
        return Response.success();
    }

    @ApiOperation(value = "测试接口2")
    @PostMapping("/test2")
    @ApiOperationLog(description = "测试异常接口")
    public Response test2(@RequestBody @Validated User user,
                         BindingResult bindingResult) {
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

    @ApiOperation(value = "测试接口")
    @PostMapping("/test")
    @ApiOperationLog(description = "测试参数校验接口")
    public Response test(@RequestBody @Validated User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return Response.fail(errorMsg);
        }
        return Response.success();
    }

}
