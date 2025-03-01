package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.DeleteCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListRequestVO;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminCategoryService {

    /**
     * 查询所有分类
     *
     * @return
     */
    Response findCategorySelectList();

    /**
     * 添加分类
     *
     * @param addCategoryRequestVO
     * @return
     */
    Response addCategory(AddCategoryRequestVO addCategoryRequestVO);

    /**
     * 分类的分页查询
     *
     * @param findCategoryPageListRequestVO
     * @return
     */
    PageResponse findCategoryPageList(FindCategoryPageListRequestVO findCategoryPageListRequestVO);

    /**
     * 删除分类
     *
     * @param deleteCategoryRequestVO
     * @return
     */
    Response deleteCategory(DeleteCategoryRequestVO deleteCategoryRequestVO);

}
