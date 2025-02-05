package com.puxinxiaolin.weblog.web.service;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagArticlePageListRequestVO;

public interface TagService {

    /**
     * 获取标签列表
     *
     * @return
     */
    Response findTagList();

    /**
     * 获取标签下文章分页列表
     *
     * @param findTagArticlePageListRequestVO
     * @return
     */
    Response findTagPageList(FindTagArticlePageListRequestVO findTagArticlePageListRequestVO);

}
