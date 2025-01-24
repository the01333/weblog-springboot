package com.puxinxiaolin.weblog.admin.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MinioConfig {

    @Resource
    private MinioProperties minioProperties;

    /**
     * 构建 Minio 客户端
     *
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        return new MinioClient.Builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

}
