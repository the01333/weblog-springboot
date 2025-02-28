package com.puxinxiaolin.weblog.web.controller;

import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.article.FindArticleByTitleRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.article.FindArticleDetailRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.article.FindIndexArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
@Api(tags = "文章")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/list")
    @ApiOperation(value = "获取首页文章分页数据")
    @ApiOperationLog(description = "获取首页文章分页数据")
    public Response findArticlePageList(@RequestBody FindIndexArticlePageListRequestVO findIndexArticlePageListRequestVO) {
        return articleService.findArticlePageList(findIndexArticlePageListRequestVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "获取文章详情")
    @ApiOperationLog(description = "获取文章详情")
    public Response findArticleDetail(@RequestBody FindArticleDetailRequestVO findArticleDetailRequestVO) {
        return articleService.findArticleDetail(findArticleDetailRequestVO);
    }

    @PostMapping("/findByLikeTitle")
    @ApiOperation(value = "根据标题模糊查找文章")
    @ApiOperationLog(description = "根据标题模糊查找文章")
    public Response findArticleByLikeTitle(@RequestBody FindArticleByTitleRequestVO requestVO) {
        return articleService.findArticleByLikeTitle(requestVO);
    }

}