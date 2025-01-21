package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminCategoryService;
import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {

    @Resource
    private AdminCategoryService adminCategoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    public Response addCategory(@RequestBody @Validated AddCategoryRequestVO addCategoryRequestVO) {
        return adminCategoryService.addCategory(addCategoryRequestVO);
    }

}
