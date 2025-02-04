package com.puxinxiaolin.weblog.web.model.vo.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArchiveArticlePageListResponseVO {

    /**
     * 归档的月份
     */
    private YearMonth month;

    private List<FindArchiveArticleResponseVO> findArchiveArticleResponseVOList;

}
