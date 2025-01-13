package com.puxinxiaolin.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description: 处理入参的 JSON 转换工具类
 * @author: YCcLin
 * @date: 2025/1/13
 **/
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将传入的对象转换为 JSON
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return object.toString();
        }
    }

}
