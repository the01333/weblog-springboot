package com.puxinxiaolin.weblog.web.service;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryArticlePageListRequestVO;

public interface CategoryService {

    /**
     * 获取分类列表
     *
     * @return
     */
    Response findCategoryList();

    /**
     * 获取分类下文章分页数据
     *
     * @param findCategoryArticlePageListRequestVO
     * @return
     */
    Response findCategoryArticlePageList(FindCategoryArticlePageListRequestVO findCategoryArticlePageListRequestVO);

}
