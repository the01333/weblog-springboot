package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.model.vo.tag.AddTagRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.DeleteTagRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.FindTagPageListRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.SearchTagRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminTagService;
import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
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
@Api(tags = "Admin 标签模块")
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Resource
    private AdminTagService adminTagService;

    @PostMapping("/add")
    @ApiOperation(value = "添加标签")
    @ApiOperationLog(description = "添加标签")
    public Response addTag(@Validated @RequestBody AddTagRequestVO addTagRequestVO) {
        return adminTagService.addTag(addTagRequestVO);
    }

    @PostMapping("/list")
    @ApiOperation(value = "标签分页数据获取")
    @ApiOperationLog(description = "标签分页数据获取")
    public PageResponse findTagPageList(@Validated @RequestBody FindTagPageListRequestVO findTagPageListRequestVO) {
        return adminTagService.findTagPageList(findTagPageListRequestVO);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除标签")
    public Response deleteTag(@Validated @RequestBody DeleteTagRequestVO deleteTagRequestVO) {
        return adminTagService.deleteTag(deleteTagRequestVO);
    }

    @PostMapping("/search")
    @ApiOperation(value = "标签模糊查询")
    @ApiOperationLog(description = "标签模糊查询")
    public Response searchTag(@Validated @RequestBody SearchTagRequestVO searchTagRequestVO) {
        return adminTagService.searchTag(searchTagRequestVO);
    }

    @PostMapping("/select/list")
    @ApiOperation(value = "查询标签 Select 列表数据")
    @ApiOperationLog(description = "查询标签 Select 列表数据")
    public Response findTagSelectList() {
        return adminTagService.findTagSelectList();
    }

}
