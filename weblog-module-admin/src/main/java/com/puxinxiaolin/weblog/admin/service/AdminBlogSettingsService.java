package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.DeleteCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListRequestVO;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminBlogSettingsService {

    /**
     * 更新博客设置信息
     *
     * @param updateBlogSettingsRequestVO
     * @return
     */
    Response updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO);

}
