package com.puxinxiaolin.weblog.web.controller;

import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.User;
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
public class TestController {

    @PostMapping("/test4")
    @ApiOperationLog(description = "测试全局异常接口")
    public Response test4(@RequestBody @Validated User user) {
        return Response.success();
    }

    @PostMapping("/test3")
    @ApiOperationLog(description = "测试其他异常接口")
    public Response test3(@RequestBody @Validated User user,
                          BindingResult bindingResult) {
        int i = 1 / 0;
        return Response.success();
    }

    @PostMapping("/test2")
    @ApiOperationLog(description = "测试异常接口")
    public Response test2(@RequestBody @Validated User user,
                         BindingResult bindingResult) {
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

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
