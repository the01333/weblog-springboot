package com.puxinxiaolin.weblog.admin.convert;

import com.puxinxiaolin.weblog.admin.model.vo.article.FindArticleDetailResponseVO;
import com.puxinxiaolin.weblog.admin.model.vo.article.FindArticlePageListResponseVO;
import com.puxinxiaolin.weblog.admin.model.vo.article.PublishArticleRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.article.UpdateArticleRequestVO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleContentDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    /**
     * ArticleDOList -> FindArticlePageListResponseVOList
     *
     * @param articleDOList
     * @return
     */
    List<FindArticlePageListResponseVO> convertArticleDOListToFindArticlePageListResponseVOList(List<ArticleDO> articleDOList);

    /**
     * ArticleDO -> FindArticleDetailResponseVO
     *
     * @param articleDO
     * @return
     */
    FindArticleDetailResponseVO convertArticleDOToFindArticleDetailResponseVO(ArticleDO articleDO);

    /**
     * UpdateArticleRequestVO -> ArticleDO
     *
     * @param updateArticleRequestVO
     * @return
     */
    ArticleDO convertUpdateArticleRequestVOToArticleDO(UpdateArticleRequestVO updateArticleRequestVO);

    /**
     * UpdateArticleRequestVO -> ArticleContentDO
     *
     * @param updateArticleRequestVO
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "articleId")
    ArticleContentDO convertUpdateArticleRequestVOToArticleContentDO(UpdateArticleRequestVO updateArticleRequestVO);

    /**
     * UpdateArticleRequestVO -> ArticleCategoryRelDO
     *
     * @param updateArticleRequestVO
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "articleId")
    ArticleCategoryRelDO convertUpdateArticleRequestVOToArticleCategoryRelDO(UpdateArticleRequestVO updateArticleRequestVO);

}
