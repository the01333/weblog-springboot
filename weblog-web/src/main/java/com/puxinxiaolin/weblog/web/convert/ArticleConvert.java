package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.web.model.vo.article.FindIndexArticlePageListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryArticlePageListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagArticlePageListResponseVO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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


    /**
     * ArticleDO -> FindCategoryArticlePageListResponseVO
     *
     * @param articleDO
     * @return
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(articleDO.getCreateTime()))")
    FindCategoryArticlePageListResponseVO convertArticleDOToFindCategoryArticlePageListResponseVO(ArticleDO articleDO);

    /**
     * List<ArticleDO> -> List<FindCategoryArticlePageListResponseVO>
     *
     * @param articleDOList
     * @return
     * @description: 迭代时自动调用 convertArticleDOToFindCategoryArticlePageListResponseVO 进行转换
     */
    @IterableMapping(elementTargetType = FindCategoryArticlePageListResponseVO.class)
    List<FindCategoryArticlePageListResponseVO> convertArticleDOListToFindCategoryArticlePageListResponseVOList(List<ArticleDO> articleDOList);

    /**
     * ArticleDO -> FindTagArticlePageListResponseVO
     *
     * @param articleDO
     * @return
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(articleDO.getCreateTime()))")
    FindTagArticlePageListResponseVO convertArticleDOToFindTagArticlePageListResponseVO(ArticleDO articleDO);

    /**
     * List<ArticleDO> -> List<FindTagArticlePageListResponseVO>
     *
     * @param articleDOList
     * @return
     * @description: 迭代时自动调用 convertArticleDOToFindTagArticlePageListResponseVO 进行转换
     */
    @IterableMapping(elementTargetType = FindTagArticlePageListResponseVO.class)
    List<FindTagArticlePageListResponseVO> convertArticleDOListToFindTagArticlePageListResponseVOList(List<ArticleDO> articleDOList);

}
