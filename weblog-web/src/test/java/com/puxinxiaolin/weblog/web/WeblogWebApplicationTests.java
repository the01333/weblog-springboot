package com.puxinxiaolin.weblog.web;

import com.puxinxiaolin.weblog.common.domain.dos.UserDO;
import com.puxinxiaolin.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@SpringBootTest
class WeblogWebApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void testInsert() {
        UserDO userDO = UserDO.builder()
                .username("程序员小林")
                .password("lyclyclyc666")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();
        userMapper.insert(userDO);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testAddSevenDaysWithLocalDate() {
        LocalDate today = LocalDate.now();
        System.out.println("今天的日期: " + today);

        // 获取七天后的日期
        LocalDate sevenDaysLater = today.plusDays(7);

        System.out.println("七天后的日期: " + sevenDaysLater);
    }

    @Test
    void testAddSevenDaysWithDate() {
        Date today = new Date();
        System.out.println("今天的日期: " + today);

        // 创建Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 7);
        Date sevenDaysLater = calendar.getTime();

        System.out.println("七天后的日期: " + sevenDaysLater);
    }

    @Test
    void testLog() {
        log.info("这是一行 Info 级别日志");
        log.warn("这是一行 Warn 级别日志");
        log.error("这是一行 Error 级别日志");

        // 占位符
        String author = "程序员小林";
        log.info("这是一行带有占位符日志，作者：{}", author);
    }
}
