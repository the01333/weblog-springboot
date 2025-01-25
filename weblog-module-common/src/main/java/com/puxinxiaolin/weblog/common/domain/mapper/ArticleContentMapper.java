package com.puxinxiaolin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleContentDO;

public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {

    /**
     * 根据文章 ID 删除文章内容
     *
     * @param articleId
     * @return
     */
    default int deleteByArticleId(Long articleId) {
        LambdaQueryWrapper<ArticleContentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleContentDO::getArticleId, articleId);
        return delete(wrapper);
    }

    /**
     * 根据文章 ID 查询文章内容
     *
     * @param articleId
     * @return
     */
    default ArticleContentDO selectByArticleId(Long articleId) {
        return selectOne(Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId));
    }

    /**
     * 根据文章 ID 更新文章内容
     *
     * @param articleContentDO
     * @return
     */
    default int updateByArticleId(ArticleContentDO articleContentDO) {
        return update(articleContentDO, Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleContentDO.getArticleId()));
    }

}
