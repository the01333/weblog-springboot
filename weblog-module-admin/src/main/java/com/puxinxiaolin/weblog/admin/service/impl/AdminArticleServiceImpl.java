package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.puxinxiaolin.weblog.admin.convert.ArticleConvert;
import com.puxinxiaolin.weblog.admin.model.vo.article.*;
import com.puxinxiaolin.weblog.admin.service.AdminArticleService;
import com.puxinxiaolin.weblog.common.domain.dos.*;
import com.puxinxiaolin.weblog.common.domain.mapper.*;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagRelMapper articleTagRelMapper;

    /**
     * 发布文章
     *
     * @param publishArticleRequestVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response publishArticle(PublishArticleRequestVO publishArticleRequestVO) {
        // 1. VO -> articleDO
        ArticleDO articleDO = ArticleConvert.INSTANCE
                .convertPublishArticleVOToArticleDO(publishArticleRequestVO);
        articleMapper.insert(articleDO);

        // 获取插入记录的主键 id
        Long articleId = articleDO.getId();

        // 2. VO -> ArticleContentDO
        ArticleContentDO articleContentDO = ArticleConvert.INSTANCE
                .convertPublishArticleVOToArticleContentDO(publishArticleRequestVO);
        articleContentDO.setArticleId(articleId);
        articleContentMapper.insert(articleContentDO);

        // 3. 处理文章关联的分类
        Long categoryId = publishArticleRequestVO.getCategoryId();
        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 选择的分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        ArticleCategoryRelDO articleCategoryRelDO = ArticleConvert.INSTANCE
                .convertPublishArticleVOToArticleCategoryRelDO(publishArticleRequestVO);
        articleCategoryRelDO.setArticleId(articleId);
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 保存文章关联的标签集合
        List<String> tagList = publishArticleRequestVO.getTagList();
        insertTagList(articleId, tagList);

        return Response.success();
    }

    /**
     * 删除文章
     *
     * @param deleteArticleRequestVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteArticle(DeleteArticleRequestVO deleteArticleRequestVO) {
        Long articleId = deleteArticleRequestVO.getId();

        // 1. 删除文章
        articleMapper.deleteById(articleId);

        // 2. 删除文章内容
        articleContentMapper.deleteByArticleId(articleId);

        // 3. 删除文章-分类关联记录
        articleCategoryRelMapper.deleteByArticleId(articleId);

        // 4. 删除文章-标签关联记录
        articleTagRelMapper.deleteByArticleId(articleId);

        return Response.success();
    }

    /**
     * 查询文章分页数据
     *
     * @param findArticlePageListRequestVO
     * @return
     */
    @Override
    public Response findArticlePageList(FindArticlePageListRequestVO findArticlePageListRequestVO) {
        String title = findArticlePageListRequestVO.getTitle();
        Long current = findArticlePageListRequestVO.getCurrent();
        Long size = findArticlePageListRequestVO.getSize();
        LocalDate startDate = findArticlePageListRequestVO.getStartDate();
        LocalDate endDate = findArticlePageListRequestVO.getEndDate();

        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(title, current, size, startDate, endDate);

        List<ArticleDO> articleDOList = articleDOPage.getRecords();

        // DO -> VO
        List<FindArticlePageListResponseVO> findArticlePageListResponseVOList = ArticleConvert.INSTANCE
                .convertArticleDOListToFindArticlePageListResponseVOList(articleDOList);

        return PageResponse.success(articleDOPage, findArticlePageListResponseVOList);
    }

    /**
     * 查询文章详情
     *
     * @param findArticleDetailRequestVO
     * @return
     */
    @Override
    public Response findArticleDetail(FindArticleDetailRequestVO findArticleDetailRequestVO) {
        Long articleId = findArticleDetailRequestVO.getId();

        ArticleDO articleDO = articleMapper.selectById(articleId);

        if (Objects.isNull(articleDO)) {
            log.warn("==> 查询的文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_EXISTED);
        }

        // content
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // article_category
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);

        // article_tag
        List<ArticleTagRelDO> articleTagRelDOList = articleTagRelMapper.selectByArticleId(articleId);
        // 获取标签 id
        List<Long> tagIdList = articleTagRelDOList.stream()
                .map(ArticleTagRelDO::getTagId)
                .collect(Collectors.toList());

        // DO -> VO
        FindArticleDetailResponseVO findArticleDetailResponseVO = ArticleConvert.INSTANCE
                .convertArticleDOToFindArticleDetailResponseVO(articleDO);
        findArticleDetailResponseVO.setContent(articleContentDO.getContent());
        findArticleDetailResponseVO.setCategoryId(articleCategoryRelDO.getCategoryId());
        findArticleDetailResponseVO.setTagList(tagIdList);

        return Response.success(findArticleDetailResponseVO);
    }

    /**
     * 更新文章
     *
     * @param updateArticleRequestVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateArticle(UpdateArticleRequestVO updateArticleRequestVO) {
        Long articleId = updateArticleRequestVO.getId();

        // 1. VO -> DO
        ArticleDO articleDO = ArticleConvert.INSTANCE
                .convertUpdateArticleRequestVOToArticleDO(updateArticleRequestVO);
        articleDO.setUpdateTime(LocalDateTime.now());
        int count = articleMapper.updateById(articleDO);
        // 根据更新是否成功，来判断该文章是否存在
        if (count == 0) {
            log.warn("==> 该文章不存在, articleId: {}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_EXISTED);
        }

        // 2. VO -> DO
        ArticleContentDO articleContentDO = ArticleConvert.INSTANCE
                .convertUpdateArticleRequestVOToArticleContentDO(updateArticleRequestVO);
        articleContentMapper.updateByArticleId(articleContentDO);

        // 3. 更新文章分类
        Long categoryId = updateArticleRequestVO.getCategoryId();
        // 3.1 校验提交的分类是否真实存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("==> 该分类不存在, categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_EXISTED);
        }
        // 先删除该文章关联的分类记录，再插入新的关联关系
        articleCategoryRelMapper.deleteByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDO = ArticleConvert.INSTANCE
                .convertUpdateArticleRequestVOToArticleCategoryRelDO(updateArticleRequestVO);
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 4. 更新文章标签
        // 先删除该文章关联的标签记录，再插入新的关联关系
        articleTagRelMapper.deleteByArticleId(articleId);
        List<String> tagList = updateArticleRequestVO.getTagList();
        insertTagList(articleId, tagList);

        return Response.success();
    }

    /**
     * 保存标签
     *
     * @param articleId
     * @param tagList
     */
    private void insertTagList(Long articleId, List<String> tagList) {
        // 表中不存在的标签（提交的不存在传的是标签名）
        List<String> notExistedTaglist = null;
        // 表中存在的标签（提交的已经存在传的是 id）
        List<String> existedTagList = null;

        // 查询出所有标签
        List<TagDO> allTagDOList = tagMapper.selectList(null);

        // 如果表中未添加任何标签
        if (CollectionUtils.isEmpty(allTagDOList)) {
            notExistedTaglist = tagList;
        } else {
            List<String> tagIdList = allTagDOList.stream()
                    .map(tagDO -> tagDO.getId().toString())
                    .collect(Collectors.toList());

            // 通过标签 ID 来筛选，包含对应 ID 则表示提交的标签是表中存在的
            existedTagList = tagList.stream()
                    .filter(tagIdList::contains)
                    .collect(Collectors.toList());
            // 否则则是不存在的
            notExistedTaglist = tagList.stream()
                    .filter(tag -> !tagIdList.contains(tag))
                    .collect(Collectors.toList());

            // 还有一种可能：按字符串名称提交上来的标签，也有可能是表中已存在的，比如表中已经有了 Java 标签，用户提交了个 java 小写的标签，需要内部装换为 Java 标签
            Map<String, Long> tagNameIdMap = allTagDOList.stream()
                    .collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));
            // 使用迭代器进行安全的删除操作
            Iterator<String> iterator = notExistedTaglist.iterator();
            while (iterator.hasNext()) {
                String notExistTag = iterator.next();
                // 转小写, 若 Map 中有相同的 key，则表示该新标签是重复标签
                if (tagNameIdMap.containsKey(notExistTag.toLowerCase())) {
                    // 从不存在的标签集合中清除
                    iterator.remove();
                    // 并将对应的 ID 添加到已存在的标签集合
                    existedTagList.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
                }
            }
        }

        // 情况1: 将提交的上来的、已存在于表中的标签，文章-标签关联关系入库
        if (!CollectionUtils.isEmpty(existedTagList)) {
            List<ArticleTagRelDO> articleTagRelDOList = new ArrayList<>();
            existedTagList.forEach(tagId -> {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.valueOf(tagId))
                        .build();
                articleTagRelDOList.add(articleTagRelDO);
            });
            // 批量插入（使用自定义的批量插入）
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOList);
        }

        // 情况2: 将提交的上来的、不存在于表中的标签，入库保存 t_tag、文章-标签关联关系入库
        if (!CollectionUtils.isEmpty(notExistedTaglist)) {
            // 需要先将标签入库，拿到对应标签 ID 后，再把文章-标签关联关系入库
            List<ArticleTagRelDO> articleTagRelDOList = new ArrayList<>();

            notExistedTaglist.forEach(tagName -> {
                TagDO tagDO = TagDO.builder()
                        .name(tagName)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                tagMapper.insert(tagDO);

                // 获取标签 id
                Long tagId = tagDO.getId();

                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .tagId(tagId)
                        .articleId(articleId)
                        .build();
                articleTagRelDOList.add(articleTagRelDO);
            });

            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOList);
        }
    }

}
