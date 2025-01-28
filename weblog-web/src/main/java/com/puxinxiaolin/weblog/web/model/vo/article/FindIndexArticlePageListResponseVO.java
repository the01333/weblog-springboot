package com.puxinxiaolin.weblog.web.model.vo.article;

import com.puxinxiaolin.weblog.web.model.vo.category.FindCategoryListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 博客前台响参
 * @author: YCcLin
 * @date: 2025/1/27
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindIndexArticlePageListResponseVO {

    private Long id;

    private String cover;

    private String title;

    private String summary;

    private LocalDate createData;

    /**
     * 文章分类
     */
    private FindCategoryListResponseVO category;

    /**
     * 文章标签
     */
    private List<FindTagListResponseVO> tagList;

}
