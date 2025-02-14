package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminDashboardService {

    /**
     * 获取仪表盘基础统计信息
     *
     * @return
     */
    Response findDashboardStatistics();

    /**
     * 获取文章发布热点统计信息
     *
     * @return
     */
    Response findDashboardPublishArticleStatistics();

    /**
     * 获取文章最近一周 PV 访问量统计信息
     *
     * @return
     */
    Response findDashboardPVStatistics();

}
