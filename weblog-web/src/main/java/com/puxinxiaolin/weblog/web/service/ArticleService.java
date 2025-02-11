package com.puxinxiaolin.weblog.web.service;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.article.FindArticleDetailRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.article.FindIndexArticlePageListRequestVO;

public interface ArticleService {

    /**
     * 获取首页文章分页数据
     *
     * @param findIndexArticlePageListRequestVO
     * @return
     */
    Response findArticlePageList(FindIndexArticlePageListRequestVO findIndexArticlePageListRequestVO);

    /**
     * 获取文章详情
     *
     * @param findArticleDetailRequestVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailRequestVO findArticleDetailRequestVO);

}
