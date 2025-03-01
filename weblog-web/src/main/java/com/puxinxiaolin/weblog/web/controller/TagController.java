package com.puxinxiaolin.weblog.web.controller;

import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.service.CategoryService;
import com.puxinxiaolin.weblog.web.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/tag")
@Api(tags = "标签")
public class TagController {

    @Resource
    private TagService tagService;

    @PostMapping("/list")
    @ApiOperation(value = "前台获取标签列表")
    @ApiOperationLog(description = "前台获取标签列表")
    public Response findCategoryList() {
        return tagService.findTagList();
    }

    @PostMapping("/article/list")
    @ApiOperation(value = "前台获取标签下文章列表")
    @ApiOperationLog(description = "前台获取标签下文章列表")
    public Response findTagPageList(@RequestBody @Validated FindTagArticlePageListRequestVO findTagArticlePageListRequestVO) {
        return tagService.findTagPageList(findTagArticlePageListRequestVO);
    }

}
