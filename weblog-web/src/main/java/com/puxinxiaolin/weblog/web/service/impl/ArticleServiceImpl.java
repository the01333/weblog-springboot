package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.admin.event.ReadArticleEvent;
import com.puxinxiaolin.weblog.admin.event.subscriber.ReadArticleSubscriber;
import com.puxinxiaolin.weblog.common.domain.dos.*;
import com.puxinxiaolin.weblog.common.domain.mapper.*;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.ArticleConvert;
import com.puxinxiaolin.weblog.web.convert.TagConvert;
import com.puxinxiaolin.weblog.web.markdown.MarkdownHelper;
import com.puxinxiaolin.weblog.web.model.vo.article.*;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import com.puxinxiaolin.weblog.web.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ArticleTagRelMapper articleTagRelMapper;
    @Resource
    private ArticleContentMapper articleContentMapper;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 获取首页文章分页数据
     *
     * @param findIndexArticlePageListRequestVO
     * @return
     */
    @Override
    public Response findArticlePageList(FindIndexArticlePageListRequestVO findIndexArticlePageListRequestVO) {
        long current = findIndexArticlePageListRequestVO.getCurrent();
        long size = findIndexArticlePageListRequestVO.getSize();

        // 1. 分页查询文章主体记录
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(null, current, size, null, null);
        List<ArticleDO> articleDOList = articleDOPage.getRecords();

        List<FindIndexArticlePageListResponseVO> findIndexArticlePageListResponseVOList = null;
        if (!CollectionUtils.isEmpty(articleDOList)) {
            // DO -> VO
            findIndexArticlePageListResponseVOList = articleDOList.stream()
                    .map(ArticleConvert.INSTANCE::convertArticleDOToFindIndexArticlePageListResponseVO)
                    .collect(Collectors.toList());

            // 获取文章 id
            List<Long> articleIdList = articleDOList.stream()
                    .map(ArticleDO::getId)
                    .collect(Collectors.toList());

            // 2. 获取文章的分类
            // 获取所有分类
            List<CategoryDO> categoryDOList = categoryMapper.selectList(Wrappers.emptyWrapper());
            // 转为 Map 便于后面根据 id 获取分类名
            Map<Long, String> categoryIdAndNameMap = categoryDOList.stream()
                    .collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));

            // 根据文章 ID 批量查询所有关联记录
            List<ArticleCategoryRelDO> articleCategoryRelDOList = articleCategoryRelMapper.selectByArticleIds(articleIdList);
            findIndexArticlePageListResponseVOList.forEach(findIndexArticlePageListResponseVO -> {
                Long currentArticleId = findIndexArticlePageListResponseVO.getId();
                // 过滤出当前文章对应的关联数据
                Optional<ArticleCategoryRelDO> relativeData = articleCategoryRelDOList.stream()
                        .filter(articleCategoryRelDO -> Objects.equals(articleCategoryRelDO.getArticleId(), currentArticleId))
                        .findAny();

                // 如果不为空
                if (relativeData.isPresent()) {
                    ArticleCategoryRelDO articleCategoryRelDO = relativeData.get();
                    Long categoryId = articleCategoryRelDO.getCategoryId();
                    // 通过分类 ID 从 map 中拿到对应的分类名称
                    String categoryName = categoryIdAndNameMap.get(categoryId);

                    FindCategoryListResponseVO findCategoryListResponseVO = FindCategoryListResponseVO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .build();
                    findIndexArticlePageListResponseVO.setCategory(findCategoryListResponseVO);
                }
            });

            // 3. 获取文章的标签
            // 获取所有标签
            List<TagDO> tagDOList = tagMapper.selectList(Wrappers.emptyWrapper());
            // 转 Map, 方便后续根据标签 ID 拿到对应的标签名称
            Map<Long, String> tagIdAndNameMap = tagDOList.stream()
                    .collect(Collectors.toMap(TagDO::getId, TagDO::getName));

            // 获取所有标签关联
            List<ArticleTagRelDO> articleTagRelDOList = articleTagRelMapper.selectByArticleIds(articleIdList);
            findIndexArticlePageListResponseVOList.forEach(findIndexArticlePageListResponseVO -> {
                Long currentArticleId = findIndexArticlePageListResponseVO.getId();
                // 过滤出当前文章的标签关联记录
                List<ArticleTagRelDO> relativeData = articleTagRelDOList.stream()
                        .filter(articleTagRelDO -> Objects.equals(articleTagRelDO.getArticleId(), currentArticleId))
                        .collect(Collectors.toList());

                List<FindTagListResponseVO> findTagListResponseVOList = new ArrayList<>();
                // 将关联记录 DO 转 VO, 并设置对应的标签名称
                relativeData.forEach(articleTagRelDO -> {
                    Long tagId = articleTagRelDO.getTagId();
                    String tagName = tagIdAndNameMap.get(tagId);

                    FindTagListResponseVO findTagListResponseVO = FindTagListResponseVO.builder()
                            .id(tagId)
                            .name(tagName)
                            .build();
                    findTagListResponseVOList.add(findTagListResponseVO);
                });
                findIndexArticlePageListResponseVO.setTagList(findTagListResponseVOList);
            });
        }

        return PageResponse.success(articleDOPage, findIndexArticlePageListResponseVOList);
    }

    /**
     * 获取文章详情
     *
     * @param findArticleDetailRequestVO
     * @return
     */
    @Override
    public Response findArticleDetail(FindArticleDetailRequestVO findArticleDetailRequestVO) {
        Long articleId = findArticleDetailRequestVO.getArticleId();

        ArticleDO articleDO = articleMapper.selectById(articleId);
        if (Objects.isNull(articleDO)) {
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_EXISTED);
        }

        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // DO -> VO
        FindArticleDetailResponseVO findArticleDetailResponseVO = FindArticleDetailResponseVO.builder()
                .title(articleDO.getTitle())
                .createTime(articleDO.getCreateTime())
                .content(MarkdownHelper.convertMarkdownToHtml(articleContentDO.getContent()))
                .readNum(articleDO.getReadNum())
                .build();

        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);
        CategoryDO categoryDO = categoryMapper.selectById(articleCategoryRelDO.getCategoryId());
        findArticleDetailResponseVO.setCategoryId(categoryDO.getId());
        findArticleDetailResponseVO.setCategoryName(categoryDO.getName());

        List<ArticleTagRelDO> articleTagRelDOList = articleTagRelMapper.selectByArticleId(articleId);
        List<Long> tagIdList = articleTagRelDOList.stream()
                .map(ArticleTagRelDO::getTagId)
                .collect(Collectors.toList());
        List<TagDO> tagDOList = tagMapper.selectByIds(tagIdList);
        // DO -> VO
        List<FindTagListResponseVO> findTagListResponseVOList = TagConvert.INSTANCE
                .convertTagDOListToFindTagListResponseVOList(tagDOList);
        findArticleDetailResponseVO.setTagList(findTagListResponseVOList);

        // 上一篇
        ArticleDO preArticleDO = articleMapper.selectPreArticle(articleId);
        if (Objects.nonNull(preArticleDO)) {
            FindPreNextArticleResponseVO findPreArticleResponseVO = FindPreNextArticleResponseVO.builder()
                    .articleId(preArticleDO.getId())
                    .articleTitle(preArticleDO.getTitle())
                    .build();
            findArticleDetailResponseVO.setPreArticle(findPreArticleResponseVO);
        }

        // 下一篇
        ArticleDO nextArticleDO = articleMapper.selectNextArticle(articleId);
        if (Objects.nonNull(nextArticleDO)) {
            FindPreNextArticleResponseVO findNextArticleResponseVO = FindPreNextArticleResponseVO.builder()
                    .articleId(nextArticleDO.getId())
                    .articleTitle(nextArticleDO.getTitle())
                    .build();
            findArticleDetailResponseVO.setNextArticle(findNextArticleResponseVO);
        }

        // 发布文章阅读事件
        applicationEventPublisher.publishEvent(new ReadArticleEvent(this, articleId));

        return Response.success(findArticleDetailResponseVO);
    }

    @Override
    public Response findArticleByLikeTitle(FindArticleByTitleRequestVO findArticleByTitleRequestVO) {
        String title = findArticleByTitleRequestVO.getTitle();
        List<ArticleDO> articles = articleMapper.selectByTitleLike(title);

        return Response.success(articles);
    }

}
