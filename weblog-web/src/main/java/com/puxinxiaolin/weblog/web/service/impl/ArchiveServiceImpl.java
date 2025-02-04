package com.puxinxiaolin.weblog.web.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleMapper;
import com.puxinxiaolin.weblog.common.utils.PageResponse;
import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.web.convert.ArchiveConvert;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticlePageListRequestVO;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticlePageListResponseVO;
import com.puxinxiaolin.weblog.web.model.vo.archive.FindArchiveArticleResponseVO;
import com.puxinxiaolin.weblog.web.service.ArchiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 获取文章归档分页数据
     *
     * @param findArchiveArticlePageListRequestVO
     * @return
     */
    @Override
    public Response findArchiveArticlePageList(FindArchiveArticlePageListRequestVO findArchiveArticlePageListRequestVO) {
        long current = findArchiveArticlePageListRequestVO.getCurrent();
        long size = findArchiveArticlePageListRequestVO.getSize();

        Page<ArticleDO> page = articleMapper.selectPageList(null, current, size, null, null);
        List<ArticleDO> articleDOList = page.getRecords();

        List<FindArchiveArticlePageListResponseVO> findArchiveArticlePageListResponseVOList = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(articleDOList)) {
            // DO -> VO
            List<FindArchiveArticleResponseVO> findArchiveArticleResponseVOList = articleDOList.stream()
                    .map(ArchiveConvert.INSTANCE::convertArticleDOToFindArchiveArticleResponseVO)
                    .collect(Collectors.toList());

            // 按创建的月份进行分组
            Map<YearMonth, List<FindArchiveArticleResponseVO>> map = findArchiveArticleResponseVOList.stream()
                    .collect(Collectors.groupingBy(FindArchiveArticleResponseVO::getCreateMonth));
            // 使用 TreeMap 按月份倒序排列
            Map<YearMonth, List<FindArchiveArticleResponseVO>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(map);

            // 遍历排序后的 Map，将其转换为归档 VO
            sortedMap.forEach((month, list) -> {
                findArchiveArticlePageListResponseVOList.add(
                        FindArchiveArticlePageListResponseVO.builder()
                                .month(month)
                                .findArchiveArticleResponseVOList(list)
                                .build()
                );
            });
        }
        return PageResponse.success(page, findArchiveArticlePageListResponseVOList);
    }

}
