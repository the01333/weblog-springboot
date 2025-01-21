package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminCategoryService {

    /**
     * 添加分类
     *
     * @param addCategoryRequestVO
     * @return
     */
    Response addCategory(AddCategoryRequestVO addCategoryRequestVO);

}
