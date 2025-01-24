package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminBlogSettingsService;
import com.puxinxiaolin.weblog.common.domain.dos.BlogSettingsDO;
import com.puxinxiaolin.weblog.common.domain.mapper.BlogSettingsMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import org.springframework.stereotype.Service;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {

    /**
     * 更新博客设置信息
     *
     * @param updateBlogSettingsRequestVO
     * @return
     */
    @Override
    public Response updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO) {
        // VO -> DO
        BlogSettingsDO blogSettingsDO = BlogSettingsDO.builder()
                .id(1L)
                .logo(updateBlogSettingsRequestVO.getLogo())
                .name(updateBlogSettingsRequestVO.getName())
                .author(updateBlogSettingsRequestVO.getAuthor())
                .introduction(updateBlogSettingsRequestVO.getIntroduction())
                .avatar(updateBlogSettingsRequestVO.getAvatar())
                .githubHomepage(updateBlogSettingsRequestVO.getGithubHomepage())
                .giteeHomepage(updateBlogSettingsRequestVO.getGiteeHomepage())
                .csdnHomepage(updateBlogSettingsRequestVO.getCsdnHomepage())
                .zhihuHomepage(updateBlogSettingsRequestVO.getZhihuHomepage())
                .build();

        // 保存或更新（当数据库中存在 ID 为 1 的记录时，则执行更新操作，否则执行插入操作）
        saveOrUpdate(blogSettingsDO);
        return Response.success();
    }

}
