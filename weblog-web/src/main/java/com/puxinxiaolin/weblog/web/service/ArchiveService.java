package com.puxinxiaolin.weblog.web.service;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticlePageListRequestVO;

public interface ArchiveService {

    /**
     * 获取文章归档分页数据
     *
     * @param findArchiveArticlePageListRequestVO
     * @return
     */
    Response findArchiveArticlePageList(FindArchiveArticlePageListRequestVO findArchiveArticlePageListRequestVO);

}
