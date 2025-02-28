package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.DeleteCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminCategoryService;
import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/category")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {

    @Resource
    private AdminCategoryService adminCategoryService;

    @PostMapping("/select/list")
    @ApiOperation(value = "分类 Select 下拉列表数据获取")
    @ApiOperationLog(description = "分类 Select 下拉列表数据获取")
    public Response findCategorySelectList() {
        return adminCategoryService.findCategorySelectList();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除分类")
    @ApiOperationLog(description = "删除分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteCategory(@RequestBody @Validated DeleteCategoryRequestVO deleteCategoryRequestVO) {
        return adminCategoryService.deleteCategory(deleteCategoryRequestVO);
    }


    @PostMapping("/list")
    @ApiOperation(value = "分类分页数据获取")
    @ApiOperationLog(description = "分类分页数据获取")
    public PageResponse findCategoryPageList(@RequestBody @Validated FindCategoryPageListRequestVO findCategoryPageListRequestVO) {
        return adminCategoryService.findCategoryPageList(findCategoryPageListRequestVO);
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addCategory(@RequestBody @Validated AddCategoryRequestVO addCategoryRequestVO) {
        return adminCategoryService.addCategory(addCategoryRequestVO);
    }

}
