package com.puxinxiaolin.weblog.admin.utils;

import com.puxinxiaolin.weblog.admin.config.MinioProperties;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @description: Minio 工具类
 * @author: YCcLin
 * @date: 2025/1/24
 **/
@Component
@Slf4j
public class MinioUtil {

    @Resource
    private MinioProperties minioProperties;

    @Resource
    private MinioClient minioClient;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadFile(MultipartFile file) throws Exception {
        if (file == null) {
            log.error("==> 上传文件异常：文件大小为空 ...");
            throw new RuntimeException("文件大小不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        String contentType = file.getContentType();

        // 存储对象的名称
        String key = UUID.randomUUID().toString()
                .replace("-", "");
        // 文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = String.format("%s%s", key, suffix);

        log.info("==> 开始上传文件至 Minio, fileName: {}", fileName);

        // 上传文件到 Minio
        minioClient.putObject(PutObjectArgs.builder()
                .contentType(contentType)
                .bucket(minioProperties.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());

        // 返回文件的访问地址
        String url = String.format("%s/%s/%s", minioProperties.getEndpoint(), minioProperties.getBucketName(), fileName);
        log.info("==> 文件上传到 Minio 成功，访问地址为: {}", url);
        return url;
    }

}
