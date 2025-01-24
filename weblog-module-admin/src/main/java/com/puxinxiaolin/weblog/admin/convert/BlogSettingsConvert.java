package com.puxinxiaolin.weblog.admin.convert;

import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.FindBlogSettingsResponseVO;
import com.puxinxiaolin.weblog.admin.model.vo.blogSettings.UpdateBlogSettingsRequestVO;
import com.puxinxiaolin.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {

    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * UpdateBlogSettingsRequestVO -> BlogSettingsDO
     *
     * @param updateBlogSettingsRequestVO
     * @return
     */
    BlogSettingsDO convertUpdateBlogSettingsRequestVOToBlogSettingsDO(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO);

    /**
     * BlogSettingsDO -> FindBlogSettingsResponseVO
     *
     * @param blogSettingsDO
     * @return
     */
    FindBlogSettingsResponseVO convertBlogSettingsDOToFindBlogSettingsResponseVO(BlogSettingsDO blogSettingsDO);

}
