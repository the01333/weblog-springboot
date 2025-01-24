package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puxinxiaolin.weblog.admin.convert.BlogSettingsConvert;
import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.FindBlogSettingsResponseVO;
import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminBlogSettingsService;
import com.puxinxiaolin.weblog.common.domain.dos.BlogSettingsDO;
import com.puxinxiaolin.weblog.common.domain.mapper.BlogSettingsMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {

    @Resource
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 更新博客设置信息
     *
     * @param updateBlogSettingsRequestVO
     * @return
     */
    @Override
    public Response updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO) {
        // VO -> DO
        BlogSettingsDO blogSettingsDO = BlogSettingsConvert.INSTANCE
                .convertUpdateBlogSettingsRequestVOToBlogSettingsDO(updateBlogSettingsRequestVO);
        blogSettingsDO.setId(1L);

        // 保存或更新（当数据库中存在 ID 为 1 的记录时，则执行更新操作，否则执行插入操作）
        saveOrUpdate(blogSettingsDO);
        return Response.success();
    }

    /**
     * 获取博客设置详情
     *
     * @return
     */
    @Override
    public Response findDetail() {
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO -> VO
        FindBlogSettingsResponseVO findBlogSettingsResponseVO = BlogSettingsConvert.INSTANCE
                .convertBlogSettingsDOToFindBlogSettingsResponseVO(blogSettingsDO);
        return Response.success(findBlogSettingsResponseVO);
    }

}
