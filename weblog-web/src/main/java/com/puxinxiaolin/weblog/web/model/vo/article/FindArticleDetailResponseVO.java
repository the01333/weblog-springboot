package com.puxinxiaolin.weblog.web.model.vo.article;

import com.puxinxiaolin.weblog.web.model.vo.tag.FindTagListResponseVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticleDetailResponseVO {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章正文（HTML）
     */
    private String content;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 分类 ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 阅读量
     */
    private Long readNum;

    /**
     * 标签集合
     */
    private List<FindTagListResponseVO> tagList;

    /**
     * 上一篇文章
     */
    private FindPreNextArticleResponseVO preArticle;

    /**
     * 下一篇文章
     */
    private FindPreNextArticleResponseVO nextArticle;

}
