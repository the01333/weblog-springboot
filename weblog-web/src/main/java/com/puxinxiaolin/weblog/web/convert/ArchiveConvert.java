package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticleResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArchiveConvert {

    ArchiveConvert INSTANCE = Mappers.getMapper(ArchiveConvert.class);

    /**
     * ArticleDO -> FindArchiveArticleResponseVO
     *
     * @param articleDO
     * @return
     */
    @Mapping(target = "createMonth", expression = "java(java.time.YearMonth.from(articleDO.getCreateTime()))")
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(articleDO.getCreateTime()))")
    FindArchiveArticleResponseVO convertArticleDOToFindArchiveArticleResponseVO(ArticleDO articleDO);

}
