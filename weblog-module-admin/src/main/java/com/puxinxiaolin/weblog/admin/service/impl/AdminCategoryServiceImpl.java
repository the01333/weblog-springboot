package com.puxinxiaolin.weblog.admin.service.impl;

import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminCategoryService;
import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Slf4j
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     *
     * @param addCategoryRequestVO
     * @return
     */
    @Override
    public Response addCategory(AddCategoryRequestVO addCategoryRequestVO) {
        String categoryName = addCategoryRequestVO.getName();

        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称: {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(categoryName.trim())
                .build();
        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }

}
