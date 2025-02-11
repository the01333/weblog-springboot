package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.TagDO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    /**
     * TagDOList -> FindTagListResponseVOList
     *
     * @param tagDOList
     * @return
     */
    List<FindTagListResponseVO> convertTagDOListToFindTagListResponseVOList(List<TagDO> tagDOList);

}
