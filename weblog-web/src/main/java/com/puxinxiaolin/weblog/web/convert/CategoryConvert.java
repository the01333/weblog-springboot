package com.puxinxiaolin.weblog.web.convert;

import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryListResponseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CategoryConvert {

    CategoryConvert INSTANCE = Mappers.getMapper(CategoryConvert.class);

    /**
     * CategoryDOList -> FindCategoryListResponseVOList
     *
     * @param categoryDOList
     * @return
     */
    List<FindCategoryListResponseVO> convertCategoryDOListToFindCategoryListResponseVOList(List<CategoryDO> categoryDOList);

}
