package com.puxinxiaolin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.puxinxiaolin.weblog.common.domain.dos.ArticleDO;
import com.puxinxiaolin.weblog.common.domain.dos.ArticlePublishCountDO;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {

    /**
     * 分页查询
     *
     * @param current   当前页码
     * @param size      每页展示的数据量
     * @param title     文章标题
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    default Page<ArticleDO> selectPageList(String title, Long current, Long size, LocalDate startDate, LocalDate endDate) {
        // 分页对象（查询第几页、每页多少数据）
        Page<ArticleDO> page = new Page<>(current, size);

        // 查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
                .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
                .orderByDesc(ArticleDO::getCreateTime);

        return selectPage(page, wrapper);
    }

    /**
     * 根据文章 ID 批量分页查询
     *
     * @param current
     * @param size
     * @param articleIds
     * @return
     */
    default Page<ArticleDO> selectPageListByArticleIds(Long current, Long size, List<Long> articleIds) {
        // 分页对象(查询第几页、每页多少数据)
        Page<ArticleDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .in(ArticleDO::getId, articleIds)
                .orderByDesc(ArticleDO::getCreateTime);
        return selectPage(page, wrapper);
    }

    /**
     * 查询上一篇文章（确保新的都在最前面）
     *
     * @param articleId
     * @return
     */
    default ArticleDO selectPreArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByAsc(ArticleDO::getId)  // 按文章 ID 升序排列
                .gt(ArticleDO::getId, articleId)  // 查询比当前文章 ID 大的
                .last("limit 1"));  // 第一条记录即为上一篇文章
    }

    /**
     * 查询下一篇文章（确保新的都在最前面）
     *
     * @param articleId
     * @return
     */
    default ArticleDO selectNextArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getId) // 按文章 ID 倒序排列
                .lt(ArticleDO::getId, articleId) // 查询比当前文章 ID 小的
                .last("limit 1")); // 第一条记录即为下一篇文章
    }

    /**
     * 阅读量 + 1
     *
     * @param articleId
     * @return
     */
    default int increaseReadNum(Long articleId) {
        return update(null, Wrappers.<ArticleDO>lambdaUpdate()
                .setSql("read_num = read_num + 1")
                .eq(ArticleDO::getId, articleId));
    }

    /**
     * 查询所有记录的阅读量
     *
     * @return
     */
    default List<ArticleDO> selectAllReadNum() {
        // 设置仅查询 read_num 字段
        return selectList(Wrappers.<ArticleDO>lambdaQuery()
                .select(ArticleDO::getReadNum));
    }

    /**
     * 按日分组，并统计每日发布的文章数量（以一年内为准）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Select("SELECT DATE(create_time) AS date, COUNT(*) AS count\n" +
            "FROM t_article\n" +
            "WHERE create_time >= '2024-11-11' AND create_time <= '2025-11-12'\n" +
            "GROUP BY DATE(create_time)")
    List<ArticlePublishCountDO> selectArticlePublishCount(LocalDate startDate, LocalDate endDate);

    @Select("SELECT * FROM t_article WHERE title LIKE concat('%', #{title}, '%')")
    List<ArticleDO> selectByTitleLike(String title);
}
