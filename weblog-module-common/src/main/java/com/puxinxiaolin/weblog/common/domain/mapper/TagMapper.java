package com.puxinxiaolin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TagMapper extends BaseMapper<TagDO> {

    /**
     * 查询分类分页数据
     *
     * @param current
     * @param size
     * @param name
     * @param startDate
     * @param endDate
     * @return
     */
    default Page<TagDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate) {
        // 分页对象
        Page<TagDO> page = new Page<>(current, size);

        // 查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(StringUtils.isNotBlank(name), TagDO::getName, name.trim())
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                .orderByDesc(TagDO::getCreateTime);

        return selectPage(page, wrapper);
    }

    /**
     * 根据标签关键词模糊查询
     *
     * @param key
     * @return
     */
    default List<TagDO> selectByKey(String key) {
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(TagDO::getName, key)
                .orderByDesc(TagDO::getCreateTime);
        return selectList(wrapper);
    }

    /**
     * 根据标签 ID 批量查询
     *
     * @param tagIdList
     * @return
     */
    default List<TagDO> selectByIds(List<Long> tagIdList) {
        return selectList(Wrappers.<TagDO>lambdaQuery()
                .in(TagDO::getId, tagIdList));
    }

}
