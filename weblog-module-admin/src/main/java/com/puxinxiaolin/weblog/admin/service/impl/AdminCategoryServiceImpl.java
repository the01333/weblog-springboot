package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListResponseVO;
import com.puxinxiaolin.weblog.admin.service.AdminCategoryService;
import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    /**
     * 分类的分页查询
     *
     * @param findCategoryPageListRequestVO
     * @return
     */
    @Override
    public PageResponse findCategoryList(FindCategoryPageListRequestVO findCategoryPageListRequestVO) {
        // 获取当前页、以及每页需要展示的数量
        long current = findCategoryPageListRequestVO.getCurrent();
        long size = findCategoryPageListRequestVO.getSize();

        // 分页对象(查询第几页、每页多少数据)
        Page<CategoryDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        String name = findCategoryPageListRequestVO.getName();
        LocalDate startDate = findCategoryPageListRequestVO.getStartDate();
        LocalDate endDate = findCategoryPageListRequestVO.getEndDate();

        wrapper
                .like(StringUtils.isNotBlank(findCategoryPageListRequestVO.getName()), CategoryDO::getName, name.trim())
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
                .orderByDesc(CategoryDO::getCreateTime);

        // 执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);

        List<CategoryDO> categoryDOList = categoryDOPage.getRecords();

        // DO -> VO
        List<FindCategoryPageListResponseVO> categoryPageListResponseVOList = null;
        if (!CollectionUtils.isEmpty(categoryDOList)) {
            categoryPageListResponseVOList = categoryDOList.stream()
                    .map(categoryDO ->
                            FindCategoryPageListResponseVO.builder()
                                    .id(categoryDO.getId())
                                    .name(categoryDO.getName())
                                    .createTime(categoryDO.getCreateTime())
                                    .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(categoryDOPage, categoryPageListResponseVOList);
    }

}
