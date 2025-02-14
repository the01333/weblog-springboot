package com.puxinxiaolin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.puxinxiaolin.weblog.admin.model.vo.dashboard.FindDashboardPVStatisticsInfoResponseVO;
import com.puxinxiaolin.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoResponseVO;
import com.puxinxiaolin.weblog.admin.service.AdminDashboardService;
import com.puxinxiaolin.weblog.common.constant.Constants;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticlePublishCountDO;
import com.puxinxiaolin.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.CategoryMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.TagMapper;
import com.puxinxiaolin.weblog.common.utils.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

    /**
     * 获取仪表盘基础统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardStatistics() {
        // 文章总数
        Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());

        // 分页总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());

        // 标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 总浏览量
        List<ArticleDO> articleDOList = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;

        if (!CollectionUtils.isEmpty(articleDOList)) {
            pvTotalCount = articleDOList.stream()
                    .mapToLong(ArticleDO::getReadNum)  // 把流中的元素映射为 Long 类型
                    .sum();
        }

        FindDashboardStatisticsInfoResponseVO findDashboardStatisticsInfoResponseVO = FindDashboardStatisticsInfoResponseVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();

        return Response.success(findDashboardStatisticsInfoResponseVO);
    }

    /**
     * 获取文章发布热点统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardPublishArticleStatistics() {
        // 当前日期
        LocalDate currDate = LocalDate.now();

        // 当前日期倒退一年的日期
        LocalDate startDate = currDate.minusYears(1);

        // 查找这一年内，每日发布的文章数量
        List<ArticlePublishCountDO> articlePublishCountDOList = articleMapper.selectArticlePublishCount(startDate, currDate.plusDays(1));
        Map<LocalDate, Long> result = null;
        if (!CollectionUtils.isEmpty(articlePublishCountDOList)) {
            // DO -> Map
            Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOList.stream()
                    .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));

            // 有序 Map, 返回的日期文章数需要以升序排列
            result = Maps.newLinkedHashMap();
            // 从上一年的今天循环到今天
            for (; startDate.isBefore(currDate) || startDate.isEqual(currDate); startDate = startDate.plusDays(1)) {
                // 以日期作为 key 从 dateArticleCountMap 中取文章发布总量
                Long count = dateArticleCountMap.get(startDate);

                result.put(startDate, count == null ? 0L : count);
            }
        }

        return Response.success(result);
    }

    /**
     * 获取文章最近一周 PV 访问量统计信息
     *
     * @return
     */
    @Override
    public Response findDashboardPVStatistics() {

        // 查询最近一周的 PV 访问量记录
        List<StatisticsArticlePVDO> statisticsArticlePVDOList = statisticsArticlePVMapper.selectLatestWeekRecords();

        Map<LocalDate, Long> pvDateCountMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(statisticsArticlePVDOList)) {
            // 转 Map, 方便后续通过日期获取 PV 访问量
            pvDateCountMap = statisticsArticlePVDOList.stream()
                    .collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate, StatisticsArticlePVDO::getPvCount));
        }

        FindDashboardPVStatisticsInfoResponseVO findDashboardPVStatisticsInfoResponseVO = null;

        // 日期集合
        List<String> pvDates = new ArrayList<>();
        // PV 集合
        List<Long> pvCounts = new ArrayList<>();

        // 当前日期
        LocalDate currDate = LocalDate.now();
        // 一周前的日期
        LocalDate lastWeekDate = currDate.minusWeeks(1);
        // 从一周前循环到今天, 存入 pvDates 和 pvCounts 中
        for (; lastWeekDate.isBefore(currDate) || lastWeekDate.isEqual(currDate); lastWeekDate = lastWeekDate.plusDays(1)) {
            pvDates.add(lastWeekDate.format(Constants.MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(lastWeekDate);
            pvCounts.add(Objects.isNull(pvCount) ? 0 : pvCount);
        }

        findDashboardPVStatisticsInfoResponseVO = FindDashboardPVStatisticsInfoResponseVO.builder()
                .pvDates(pvDates)
                .pvCounts(pvCounts)
                .build();

        return Response.success(findDashboardPVStatisticsInfoResponseVO);
    }

}
