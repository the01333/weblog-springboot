package com.puxinxiaolin.weblog.admin.convert;

import com.puxinxiaolin.weblog.common.domain.dos.TagDO;
import com.puxinxiaolin.weblog.common.model.vo.SelectResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface TagConvert {

    TagConvert INSTANCE = Mappers.getMapper(TagConvert.class);

    /**
     * TagDOList -> SelectResponseVOList
     *
     * @param tagDOList
     * @return
     */
    default List<SelectResponseVO> convertTagDOListToSelectResponseVOList(List<TagDO> tagDOList) {
        if (tagDOList == null) {
            return null;
        }

        List<SelectResponseVO> list = new ArrayList<>(tagDOList.size());
        for (TagDO tagDO : tagDOList) {
            list.add(tagDOToSelectResponseVO(tagDO));
        }

        return list;
    }

    default SelectResponseVO tagDOToSelectResponseVO(TagDO tagDO) {
        if (tagDO == null) {
            return null;
        }

        return SelectResponseVO.builder()
                .label(tagDO.getName())
                .value(tagDO.getId())
                .build();
    }

}
