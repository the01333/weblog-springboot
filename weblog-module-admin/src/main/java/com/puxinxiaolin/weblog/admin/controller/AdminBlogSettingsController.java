package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminBlogSettingsService;
import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
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
@RequestMapping("/admin/blog/settings")
@Api(tags = "Admin 博客设置模块")
public class AdminBlogSettingsController {

    @Resource
    private AdminBlogSettingsService blogSettingsService;

    @PostMapping("/update")
    @ApiOperation(value = "博客基础信息修改")
    @ApiOperationLog(description = "博客基础信息修改")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateBlogSettings(@RequestBody @Validated UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO) {
        return blogSettingsService.updateBlogSettings(updateBlogSettingsRequestVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "获取博客设置详情")
    @ApiOperationLog(description = "获取博客设置详情")
    public Response findDetail() {
        return blogSettingsService.findDetail();
    }

}
