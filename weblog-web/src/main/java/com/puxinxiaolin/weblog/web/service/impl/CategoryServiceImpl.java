package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.CategoryConvert;
import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryListResponseVO;
import com.puxinxiaolin.weblog.web.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 获取分类列表
     *
     * @return
     */
    @Override
    public Response findCategoryList() {
        List<CategoryDO> categoryDOList = categoryMapper.selectList(Wrappers.emptyWrapper());

        // DO -> VO
        List<FindCategoryListResponseVO> findCategoryListResponseVOList = CategoryConvert.INSTANCE
                .convertCategoryDOListToFindCategoryListResponseVOList(categoryDOList);
        return Response.success(findCategoryListResponseVOList);
    }

}
