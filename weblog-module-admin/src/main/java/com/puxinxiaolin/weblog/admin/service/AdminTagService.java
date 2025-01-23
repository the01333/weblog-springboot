package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.tag.AddTagRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.DeleteTagRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.FindTagPageListRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.tag.SearchTagRequestVO;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 新增标签
     *
     * @param addTagRequestVO
     * @return
     */
    Response addTag(AddTagRequestVO addTagRequestVO);

    /**
     * 查询标签分页数据
     *
     * @param findTagPageListRequestVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListRequestVO findTagPageListRequestVO);

    /**
     * 删除标签
     *
     * @param deleteTagRequestVO
     * @return
     */
    Response deleteTag(DeleteTagRequestVO deleteTagRequestVO);

    /**
     * 标签模糊查询
     *
     * @param searchTagRequestVO
     * @return
     */
    Response searchTag(SearchTagRequestVO searchTagRequestVO);
}
