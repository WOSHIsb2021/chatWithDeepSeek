package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * JSON 工具类
 * 
 * 封装了 Jackson 库的常用操作，用于对象与 JSON 字符串之间的转换。
 * 
 */
public class JsonUtil {
    /** Jackson ObjectMapper 实例，线程安全 */
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 注册 JavaTimeModule 以支持 Java 8 的 LocalDateTime 等时间类型
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param object 要转换的对象
     * @return JSON 字符串，如果转换失败则返回 null
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将 JSON 字符串转换为对象
     *
     * @param json  JSON 字符串
     * @param clazz 目标类的 Class 对象
     * @param <T>   目标类型
     * @return 转换后的对象，如果转换失败则返回 null
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
