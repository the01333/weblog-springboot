package com.puxinxiaolin.weblog.admin.controller;

import com.puxinxiaolin.weblog.admin.service.AdminFileService;
import com.puxinxiaolin.weblog.common.aspect.ApiOperationLog;
import com.puxinxiaolin.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
@Api(tags = "Admin 文件模块")
public class AdminFileController {

    @Resource
    private AdminFileService adminFileService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    @ApiOperationLog(description = "上传文件")
    public Response uploadFile(@RequestParam MultipartFile file) {
        return adminFileService.uploadFile(file);
    }

}
