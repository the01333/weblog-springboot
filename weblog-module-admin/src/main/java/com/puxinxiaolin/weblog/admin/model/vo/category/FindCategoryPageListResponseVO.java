package com.puxinxiaolin.weblog.admin.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description: 分页查询出参 VO
 *
 * @author: YCcLin
 * @date: 2025/1/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryPageListResponseVO {

    /**
     * 分类 id
     */
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建的起始日期
     */
    private LocalDateTime createTime;

}
