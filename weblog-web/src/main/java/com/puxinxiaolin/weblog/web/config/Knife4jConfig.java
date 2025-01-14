package com.puxinxiaolin.weblog.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @description: Knife4j 配置
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Configuration
@EnableSwagger2WebMvc
@Profile(value = "dev")     // 只有在 dev 环境下生效
public class Knife4jConfig {

    @Bean("webApi")
    public Docket createApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                // 分组名称
                .groupName("Web 前台接口")
                .select()
                // 这里指定 Controller 扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.puxinxiaolin.weblog.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 API 信息
     *
     * @return
     */
    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("Weblog 博客前台接口文档")
                .description("Weblog 是一款由 Spring Boot + Vue 3.2 + Vite 4.3 开发的前后端分离博客。")
                .termsOfServiceUrl("https://www.cnblogs.com/pxxl")
                .contact(new Contact("程序员小林", "https://www.cnblogs.com/pxxl", "3149696140@qq.com"))
                .version("1.0")
                .build();
    }

}
