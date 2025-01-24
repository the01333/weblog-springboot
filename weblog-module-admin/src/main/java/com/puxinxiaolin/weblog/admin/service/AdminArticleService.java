package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.article.PublishArticleRequestVO;
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

}
