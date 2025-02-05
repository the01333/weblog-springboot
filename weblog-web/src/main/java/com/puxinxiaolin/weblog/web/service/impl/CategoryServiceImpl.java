package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.ArticleConvert;
import com.puxinxiaolin.weblog.web.convert.CategoryConvert;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryArticlePageListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryListResponseVO;
import com.puxinxiaolin.weblog.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public Response findCategoryList() {
        List<CategoryDO> categoryDOList = categoryMapper.selectList(Wrappers.emptyWrapper());

        // DO -> VO
        List<FindCategoryListResponseVO> findCategoryListResponseVOList = CategoryConvert.INSTANCE
                .convertCategoryDOListToFindCategoryListResponseVOList(categoryDOList);
        return Response.success(findCategoryListResponseVOList);
    }

    /**
     * 获取分类下文章分页数据
     *
     * @param findCategoryArticlePageListRequestVO
     * @return
     */
    @Override
    public Response findCategoryArticlePageList(FindCategoryArticlePageListRequestVO findCategoryArticlePageListRequestVO) {
        long current = findCategoryArticlePageListRequestVO.getCurrent();
        long size = findCategoryArticlePageListRequestVO.getSize();
        Long categoryId = findCategoryArticlePageListRequestVO.getId();

        // 判断该分类是否存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 该分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }

        // 先查询该分类下所有关联的文章 ID
        List<ArticleCategoryRelDO> articleCategoryRelDOList = articleCategoryRelMapper.selectListByCategoryId(categoryId);
        // 若该分类下未发布任何文章
        if (CollectionUtils.isEmpty(articleCategoryRelDOList)) {
            log.info("==> 该分类下还未发布任何文章, categoryId: {}", categoryId);
            return PageResponse.success(null, null);
        }

        List<Long> articleIdList = articleCategoryRelDOList.stream()
                .map(ArticleCategoryRelDO::getArticleId)
                .collect(Collectors.toList());
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIdList);
        List<ArticleDO> articleDOList = page.getRecords();

        // DO -> VO
        List<FindCategoryArticlePageListResponseVO> findCategoryArticlePageListResponseVOList = ArticleConvert.INSTANCE
                .convertArticleDOListToFindCategoryArticlePageListResponseVOList(articleDOList);

        return PageResponse.success(page, findCategoryArticlePageListResponseVOList);
    }

}
