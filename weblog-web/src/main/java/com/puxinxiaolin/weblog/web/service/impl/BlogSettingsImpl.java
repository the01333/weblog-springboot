package com.puxinxiaolin.weblog.web.service.impl;

import com.puxinxiaolin.weblog.common.domain.dos.BlogSettingsDO;
import com.puxinxiaolin.weblog.common.domain.mapper.BlogSettingsMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.BlogSettingsConvert;
import com.puxinxiaolin.weblog.web.model.vo.blogSettings.FindBlogSettingsDetailResponseVO;
import com.puxinxiaolin.weblog.web.service.BlogSettings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogSettingsImpl implements BlogSettings {

    @Resource
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客详情
     *
     * @return
     */
    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);

        // DO -> VO
        FindBlogSettingsDetailResponseVO findBlogSettingsDetailResponseVO = BlogSettingsConvert.INSTANCE
                .convertBlogSettingsDOToFindBlogSettingsDetailResponseVO(blogSettingsDO);

        return Response.success(findBlogSettingsDetailResponseVO);
    }

}
