package com.puxinxiaolin.weblog.admin.schedule;

import com.puxinxiaolin.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.puxinxiaolin.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description: 手动触发, 项目启动自动新增明日数据待发布订阅模式进行触发
 * @author: YCcLin
 * @date: 2025/2/13
 **/
@Slf4j
//@Component
public class ManualInitPVRecordScheduledTask {

    @Resource
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

    @PostConstruct
    public void execute() {
        log.info("==> 开始执行初始化明日 PV 访问量记录任务");

        LocalDate currDate = LocalDate.now();
        LocalDate tomorrowDate = currDate.plusDays(1);

        StatisticsArticlePVDO statisticsArticlePVDO = StatisticsArticlePVDO.builder()
                .pvDate(tomorrowDate)
                .pvCount(0L)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        statisticsArticlePVMapper.insert(statisticsArticlePVDO);
        log.info("==> 结束执行初始化明日 PV 访问量记录任务");

    }

}
