package org.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 聊天消息模型类
 * <p>
 * 表示一条聊天记录，包含发送者、内容、时间戳和会话ID。
 * </p>
 */
public class ChatMessage {

    /**
     * 消息发送者枚举
     */
    public enum Sender {
        /** 用户 */
        USER,
        /** AI 助手 */
        ASSISTANT,
        /** 系统消息 */
        SYSTEM
    }

    /** 发送者 */
    private Sender sender;
    /** 消息内容 */
    private String content;
    /** 发送时间 */
    private LocalDateTime timestamp;
    /** 所属会话 ID */
    private String sessionId;

    /**
     * 无参构造函数
     * <p>
     * Jackson 反序列化必须需要无参构造函数。
     * </p>
     */
    public ChatMessage() {
    }

    /**
     * 全参构造函数
     *
     * @param sender    发送者
     * @param content   消息内容
     * @param sessionId 会话 ID
     */
    public ChatMessage(Sender sender, String content, String sessionId) {
        this.sender = sender;
        this.content = content;
        this.sessionId = sessionId;
        this.timestamp = LocalDateTime.now(); // 默认使用当前时间
    }

    // Getter & Setter 方法

    /**
     * 设置发送者
     * @param sender 发送者枚举
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * 获取发送者
     * @return 发送者枚举
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * 获取消息内容
     * @return 消息内容字符串
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     * @param content 消息内容字符串
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取时间戳
     * @return 发送时间
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * 设置时间戳
     * @param timestamp 发送时间
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取会话 ID
     * @return 会话 ID
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 设置会话 ID
     * @param sessionId 会话 ID
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 辅助方法：获取格式化后的时间字符串
     *
     * @return 格式为 "HH:mm:ss" 的时间字符串
     */
    public String getFormattedTime() {
        return timestamp.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * 重写 toString 方法
     *
     * @return 格式化的消息字符串，例如："[12:00:00]USER: 你好"
     */
    @Override
    public String toString() {
        // 自定义输出格式：[时间]发送者: 内容
        return String.format("[%s]%s:%s", getFormattedTime(), sender, content);
    }

}
