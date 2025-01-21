package com.puxinxiaolin.weblog.common.model;

import lombok.Data;

@Data
public class BasePageQuery {

    /**
     * 当前页码, 默认第一页
     */
    private long current = 1L;

    /**
     * 每页显示条数, 默认每页显示 10 条
     */
    private long size = 10L;

}
