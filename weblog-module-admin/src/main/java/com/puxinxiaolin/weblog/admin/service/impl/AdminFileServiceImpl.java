package com.puxinxiaolin.weblog.admin.service.impl;

import com.puxinxiaolin.weblog.admin.model.vo.file.UploadFileResponseVO;
import com.puxinxiaolin.weblog.admin.service.AdminFileService;
import com.puxinxiaolin.weblog.admin.utils.MinioUtil;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {

    @Resource
    private MinioUtil minioUtil;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public Response uploadFile(MultipartFile file) {
        try {
            String url = minioUtil.uploadFile(file);

            return Response.success(UploadFileResponseVO.builder().url(url).build());
        } catch (Exception e) {
            log.error("==> 上传文件到 Minio 错误: {}", e.getMessage());
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }

}
