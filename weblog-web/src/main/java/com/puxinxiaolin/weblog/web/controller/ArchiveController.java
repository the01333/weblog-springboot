package com.puxinxiaolin.weblog.web.controller;

import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.service.ArchiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(tags = "文章归档")
public class ArchiveController {

    @Resource
    private ArchiveService archiveService;

    @PostMapping("/archive/list")
    @ApiOperation(value = "获取文章归档分页数据")
    @ApiOperationLog(description = "获取文章归档分页数据")
    public Response findArchivePageList(@RequestBody FindArchiveArticlePageListRequestVO findArchiveArticlePageListRequestVO) {
        return archiveService.findArchiveArticlePageList(findArchiveArticlePageListRequestVO);
    }

}
