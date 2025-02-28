package com.puxinxiaolin.weblog.web.model.vo.article;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "模糊查询文章详情 VO")
public class FindArticleByTitleRequestVO {

    /**
     * 文章标题
     */
    private String title;

}