package com.puxinxiaolin.weblog.web.model.vo.archive;

import com.puxinxiaolin.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "文章归档分页 VO")
public class FindArchiveArticlePageListRequestVO extends BasePageQuery {
}
