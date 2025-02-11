package com.puxinxiaolin.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description: 事件发布者
 * @author: YCcLin
 * @date: 2025/2/11
 **/
@Getter
public class ReadArticleEvent extends ApplicationEvent {

    /**
     * 文章 ID
     */
    private Long articleId;

    public ReadArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
