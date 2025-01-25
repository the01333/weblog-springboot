package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.puxinxiaolin.weblog.admin.model.vo.tag.*;
import com.puxinxiaolin.weblog.admin.service.AdminTagService;
import com.puxinxiaolin.weblog.common.domain.dos.TagDO;
import com.puxinxiaolin.weblog.common.domain.mapper.TagMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.model.vo.SelectResponseVO;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Resource
    private TagMapper tagMapper;

    /**
     * 新增标签
     *
     * @param addTagRequestVO
     * @return
     */
    @Override
    public Response addTag(AddTagRequestVO addTagRequestVO) {
        // VO -> DO
        List<TagDO> tagDOList = addTagRequestVO.getTagList().stream()
                .map(tag -> TagDO.builder()
                        .name(tag.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        try {
            saveBatch(tagDOList);
        } catch (Exception e) {
            // 允许添加重复标签
            log.warn(ResponseCodeEnum.TAG_CANT_DUPLICATE.getErrorMessage(), e);
        }

        return Response.success();
    }

    /**
     * 查询标签分页数据
     *
     * @param findTagPageListRequestVO
     * @return
     */
    @Override
    public PageResponse findTagPageList(FindTagPageListRequestVO findTagPageListRequestVO) {
        String name = findTagPageListRequestVO.getName();
        LocalDate startDate = findTagPageListRequestVO.getStartDate();
        LocalDate endDate = findTagPageListRequestVO.getEndDate();
        long current = findTagPageListRequestVO.getCurrent();
        long size = findTagPageListRequestVO.getSize();

        Page<TagDO> page = tagMapper.selectPageList(current, size, name, startDate, endDate);

        List<TagDO> tagDOList = page.getRecords();
        // DO -> VO
        List<FindTagPageListResponseVO> tagPageListResponseVOList = null;
        if (!CollectionUtils.isEmpty(tagDOList)) {
            tagPageListResponseVOList = tagDOList.stream()
                    .map(tagDO -> FindTagPageListResponseVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .createTime(tagDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(page, tagPageListResponseVOList);
    }

    /**
     * 删除标签
     *
     * @param deleteTagRequestVO
     * @return
     */
    @Override
    public Response deleteTag(DeleteTagRequestVO deleteTagRequestVO) {
        int count = tagMapper.deleteById(deleteTagRequestVO.getId());

        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

    /**
     * 标签模糊查询
     *
     * @param searchTagRequestVO
     * @return
     */
    @Override
    public Response searchTag(SearchTagRequestVO searchTagRequestVO) {
        String key = searchTagRequestVO.getKey();

        List<TagDO> tagDOList = tagMapper.selectByKey(key);
        // DO -> VO
        List<SelectResponseVO> selectResponseVOList = null;
        if (!CollectionUtils.isEmpty(tagDOList)) {
            selectResponseVOList = tagDOList.stream()
                    .map(tagDO -> SelectResponseVO.builder()
                            .value(tagDO.getId())
                            .label(tagDO.getName())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(selectResponseVOList);
    }

}
