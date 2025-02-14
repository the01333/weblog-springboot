package com.puxinxiaolin.weblog.admin.schedule;

import com.puxinxiaolin.weblog.common.domain.dos.StatisticsArticlePVDO;
import com.puxinxiaolin.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class InitPVRecordScheduledTask {

    @Resource
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

    // 每天晚间 23 点执行
    @Scheduled(cron = "0 0 23 * * ?")
    public void execute() {
        // 定时任务执行的业务逻辑
        log.info("==> 开始执行初始化明日 PV 访问量记录定时任务");

        LocalDate currDate = LocalDate.now();
        LocalDate tomorrowDate = currDate.plusDays(1);

        StatisticsArticlePVDO statisticsArticlePVDO = StatisticsArticlePVDO.builder()
                .pvDate(tomorrowDate)
                .pvCount(0L)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        statisticsArticlePVMapper.insert(statisticsArticlePVDO);
        log.info("==> 结束执行初始化明日 PV 访问量记录定时任务");

    }

}
