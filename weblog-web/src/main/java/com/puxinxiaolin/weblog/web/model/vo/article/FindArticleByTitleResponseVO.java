package com.puxinxiaolin.weblog.web.model.vo.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticleByTitleResponseVO {

    private Long id;

    private String title;

    private String content;

}