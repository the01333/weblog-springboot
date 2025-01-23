package com.puxinxiaolin.weblog.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 通用的下拉框数据响应类
 * @author: YCcLin
 * @date: 2025/1/21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectResponseVO {

    /**
     * Select 下拉列表的展示文字
     */
    private String label;

    /**
     * Select 下拉列表的 value 值，如 ID 等
     */
    private Object value;

}
