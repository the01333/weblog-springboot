package com.puxinxiaolin.weblog.admin.model.vo.dashboard;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询仪表盘文章 PV 访问量信息出参 VO")
public class FindDashboardPVStatisticsInfoResponseVO {

    /**
     * 日期集合
     */
    private List<String> pvDates;

    /**
     * PV 访问量集合
     */
    private List<Long> pvCounts;

}
