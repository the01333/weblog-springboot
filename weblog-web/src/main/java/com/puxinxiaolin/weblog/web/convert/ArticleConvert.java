package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.web.model.vo.article.FindIndexArticlePageListResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    /**
     * 将ArticleDO -> FindIndexArticlePageListResponseVO
     *
     * @param articleDO
     * @return
     */
    @Mapping(target = "createData", expression = "java(java.time.LocalDate.from(articleDO.getCreateTime()))")
    FindIndexArticlePageListResponseVO convertArticleDOToFindIndexArticlePageListResponseVO(ArticleDO articleDO);

}
