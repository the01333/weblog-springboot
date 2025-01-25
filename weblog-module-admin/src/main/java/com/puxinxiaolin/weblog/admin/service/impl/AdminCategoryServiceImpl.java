package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.admin.model.vo.category.AddCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.DeleteCategoryRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.category.FindCategoryPageListResponseVO;
import com.puxinxiaolin.weblog.admin.service.AdminCategoryService;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.puxinxiaolin.weblog.common.domain.dos.CategoryDO;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.exception.BizException;
import com.puxinxiaolin.weblog.common.model.vo.SelectResponseVO;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
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

    @Resource
    private ArticleCategoryRelMapper articleCategoryRelMapper;

    /**
     * 查询所有分类
     *
     * @return
     */
    @Override
    public Response findCategorySelectList() {
        // 查询所有分类
        List<CategoryDO> categoryDOList = categoryMapper.selectList(null);

        // DO -> VO
        List<SelectResponseVO> selectResponseVOList = null;
        if (!CollectionUtils.isEmpty(categoryDOList)) {
            // 将分类 ID 作为 Value 值，将分类名称作为 label 展示
            selectResponseVOList = categoryDOList.stream()
                    .map(categoryDO -> SelectResponseVO.builder()
                            .label(categoryDO.getName())
                            .value(categoryDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }
        return Response.success(selectResponseVOList);
    }

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
    public PageResponse findCategoryPageList(FindCategoryPageListRequestVO findCategoryPageListRequestVO) {
        String name = findCategoryPageListRequestVO.getName();
        LocalDate startDate = findCategoryPageListRequestVO.getStartDate();
        LocalDate endDate = findCategoryPageListRequestVO.getEndDate();
        long current = findCategoryPageListRequestVO.getCurrent();
        long size = findCategoryPageListRequestVO.getSize();

        Page<CategoryDO> page = categoryMapper.selectPageList(current, size, name, startDate, endDate);

        List<CategoryDO> categoryDOList = page.getRecords();
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

        return PageResponse.success(page, categoryPageListResponseVOList);
    }

    /**
     * 删除分类
     *
     * @param deleteCategoryRequestVO
     * @return
     */
    @Override
    public Response deleteCategory(DeleteCategoryRequestVO deleteCategoryRequestVO) {
        Long categoryId = deleteCategoryRequestVO.getId();

        // 校验该分类下是否已经有文章，若有，则提示需要先删除分类下所有文章，才能删除
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectOneByCategoryId(categoryId);
        if (Objects.nonNull(articleCategoryRelDO)) {
            log.warn("==> 此分类下包含文章，无法删除，categoryId: {}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_CAN_NOT_DELETE);
        }

        categoryMapper.deleteById(categoryId);
        return Response.success();
    }

}
