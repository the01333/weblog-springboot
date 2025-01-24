package com.puxinxiaolin.weblog.admin.convert;

import com.puxinxiaolin.weblog.admin.model.vo.article.PublishArticleRequestVO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleContentDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {

    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    /**
     * PublishArticleRequestVO -> ArticleDO
     *
     * @param publishArticleRequestVO
     * @return
     */
    ArticleDO convertPublishArticleVOToArticleDO(PublishArticleRequestVO publishArticleRequestVO);

    /**
     * PublishArticleRequestVO -> ArticleContentDO
     *
     * @param publishArticleRequestVO
     * @return
     */
    ArticleContentDO convertPublishArticleVOToArticleContentDO(PublishArticleRequestVO publishArticleRequestVO);

    /**
     * PublishArticleRequestVO -> ArticleCategoryRelDO
     *
     * @param publishArticleRequestVO
     * @return
     */
    ArticleCategoryRelDO convertPublishArticleVOToArticleCategoryRelDO(PublishArticleRequestVO publishArticleRequestVO);

}
