package com.puxinxiaolin.weblog.web.model.vo.article;

import com.puxinxiaolin.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "首页查询文章分页 VO")
public class FindIndexArticlePageListRequestVO extends BasePageQuery {
}
