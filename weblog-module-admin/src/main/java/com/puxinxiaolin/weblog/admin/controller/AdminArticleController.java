package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.model.vo.article.*;
import com.puxinxiaolin.weblog.admin.service.AdminArticleService;
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
@RequestMapping("/admin/article")
@Api(tags = "Admin 文章模块")
public class AdminArticleController {

    @Resource
    private AdminArticleService adminArticleService;

    @PostMapping("/publish")
    @ApiOperation(value = "文章发布")
    @ApiOperationLog(description = "文章发布")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response publishArticle(@RequestBody @Validated PublishArticleRequestVO publishArticleRequestVO) {
        return adminArticleService.publishArticle(publishArticleRequestVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "文章删除")
    @ApiOperationLog(description = "文章删除")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteArticle(@RequestBody @Validated DeleteArticleRequestVO deleteArticleRequestVO) {
        return adminArticleService.deleteArticle(deleteArticleRequestVO);
    }

    @PostMapping("/list")
    @ApiOperation(value = "查询文章分页数据")
    @ApiOperationLog(description = "查询文章分页数据")
    public Response findArticlePageList(@RequestBody @Validated FindArticlePageListRequestVO findArticlePageListRequestVO) {
        return adminArticleService.findArticlePageList(findArticlePageListRequestVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "查询文章详情")
    @ApiOperationLog(description = "查询文章详情")
    public Response findArticleDetail(@RequestBody @Validated FindArticleDetailRequestVO findArticleDetailRequestVO) {
        return adminArticleService.findArticleDetail(findArticleDetailRequestVO);
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新文章")
    @ApiOperationLog(description = "更新文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateArticle(@RequestBody @Validated UpdateArticleRequestVO updateArticleRequestVO) {
        return adminArticleService.updateArticle(updateArticleRequestVO);
    }

}
