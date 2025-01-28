package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.BlogSettingsDO;
import com.puxinxiaolin.weblog.web.model.vo.blogSettings.FindBlogSettingsDetailResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {

    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * BlogSettingsDO -> FindBlogSettingsDetailResponseVO
     *
     * @param blogSettingsDO
     * @return
     */
    FindBlogSettingsDetailResponseVO convertBlogSettingsDOToFindBlogSettingsDetailResponseVO(BlogSettingsDO blogSettingsDO);

}
