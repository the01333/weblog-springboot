package com.puxinxiaolin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

}
