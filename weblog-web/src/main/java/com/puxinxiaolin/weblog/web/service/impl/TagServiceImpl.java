package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleTagRelDO;
import com.puxinxiaolin.weblog.common.domain.dos.TagDO;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.TagMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.ArticleConvert;
import com.puxinxiaolin.weblog.web.convert.TagConvert;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagArticlePageListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import com.puxinxiaolin.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagRelMapper articleTagRelMapper;

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 获取标签列表
     *
     * @return
     */
    @Override
    public Response findTagList() {
        List<TagDO> tagDOList = tagMapper.selectList(Wrappers.emptyWrapper());

        // DO -> VO
        List<FindTagListResponseVO> findTagListResponseVOList = TagConvert.INSTANCE
                .convertTagDOListToFindTagListResponseVOList(tagDOList);

        return Response.success(findTagListResponseVOList);
    }

    /**
     * 获取标签下文章分页列表
     *
     * @param findTagArticlePageListRequestVO
     * @return
     */
    @Override
    public Response findTagPageList(FindTagArticlePageListRequestVO findTagArticlePageListRequestVO) {
        Long tagId = findTagArticlePageListRequestVO.getId();
        long current = findTagArticlePageListRequestVO.getCurrent();
        long size = findTagArticlePageListRequestVO.getSize();

        // 判断标签是否存在
        TagDO tagDO = tagMapper.selectById(tagId);
        if (Objects.isNull(tagDO)) {
            log.warn("==> 该标签不存在, tagId: {}", tagId);
            throw new BizException(ResponseCodeEnum.TAG_NOT_EXISTED);
        }

        List<ArticleTagRelDO> articleTagRelDOList = articleTagRelMapper.selectByTagId(tagId);
        // 若该标签下未发布任何文章
        if (CollectionUtils.isEmpty(articleTagRelDOList)) {
            log.info("==> 该标签下还未发布任何文章, tagId: {}", tagId);
            return PageResponse.success(null, null);
        }

        List<Long> articleIdList = articleTagRelDOList.stream()
                .map(ArticleTagRelDO::getArticleId)
                .collect(Collectors.toList());

        // 根据文章 ID 集合查询文章分页数据
        Page<ArticleDO> page = articleMapper.selectPageListByArticleIds(current, size, articleIdList);
        List<ArticleDO> articleDOList = page.getRecords();

        // DO -> VO
        List<FindTagArticlePageListResponseVO> findTagArticlePageListResponseVOList = ArticleConvert.INSTANCE
                .convertArticleDOListToFindTagArticlePageListResponseVOList(articleDOList);

        return PageResponse.success(page, findTagArticlePageListResponseVOList);
    }

}
