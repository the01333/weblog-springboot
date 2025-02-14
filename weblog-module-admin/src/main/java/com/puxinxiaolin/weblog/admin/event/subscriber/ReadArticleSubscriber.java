package com.puxinxiaolin.weblog.admin.event.subscriber;

import com.puxinxiaolin.weblog.admin.event.ReadArticleEvent;
import com.puxinxiaolin.weblog.common.domain.mapper.ArticleMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.StatisticsArticlePVMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @description: 事件订阅者
 * @author: YCcLin
 * @date: 2025/2/11
 **/
@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

    /**
     * 处理收到的事件, 可以是任何逻辑操作
     *
     * @param event
     */
    @Async(value = "threadPoolTaskExecutor")   // 指定线程池（未指定的话 Spring 使用默认线程池）
    @Override
    public void onApplicationEvent(ReadArticleEvent event) {

        Long articleId = event.getArticleId();

        // 获取当前线程名称
        String threadName = Thread.currentThread().getName();

        log.info("==> threadName: {}", threadName);
        log.info("==> 文章阅读事件消费成功, articleId: {}", articleId);

        // 执行文章阅读量 +1
        articleMapper.increaseReadNum(articleId);
        log.info("==> 文章阅读量 +1 操作成功，articleId: {}", articleId);

        // 当日 文章 PV 访问量 + 1
        LocalDate currDate = LocalDate.now();
        statisticsArticlePVMapper.increasePVCount(currDate);
        log.info("==> 当日文章 PV 访问量 + 1 操作成功, date: {}", currDate);

    }

}
