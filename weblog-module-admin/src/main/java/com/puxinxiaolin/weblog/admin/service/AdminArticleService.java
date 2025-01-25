package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.article.*;
import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminArticleService {

    /**
     * 发布文章
     *
     * @param publishArticleRequestVO
     * @return
     */
    Response publishArticle(PublishArticleRequestVO publishArticleRequestVO);

    /**
     * 删除文章
     *
     * @param deleteArticleRequestVO
     * @return
     */
    Response deleteArticle(DeleteArticleRequestVO deleteArticleRequestVO);

    /**
     * 查询文章分页数据
     *
     * @param findArticlePageListRequestVO
     * @return
     */
    Response findArticlePageList(FindArticlePageListRequestVO findArticlePageListRequestVO);

    /**
     * 查询文章详情
     *
     * @param findArticleDetailRequestVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailRequestVO findArticleDetailRequestVO);

    /**
     * 更新文章
     *
     * @param updateArticleRequestVO
     * @return
     */
    Response updateArticle(UpdateArticleRequestVO updateArticleRequestVO);

}
