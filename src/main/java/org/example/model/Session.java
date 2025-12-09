package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会话模型类
 * <p>
 * 表示一次聊天会话，包含会话 ID、开始时间和消息列表。
 * </p>
 */
public class Session {
    /** 会话唯一标识符 */
    private String sessionId;
    /** 会话开始时间 */
    private LocalDateTime startTime;
    /** 消息列表，存储该会话的所有聊天记录 */
    private List<ChatMessage> messages;

    /**
     * 构造函数
     *
     * @param sessionId 会话 ID
     */
    public Session(String sessionId) {
        this.sessionId = sessionId;
        this.startTime = LocalDateTime.now();
        this.messages = new ArrayList<>();
    }

    /**
     * 添加一条消息到会话中
     *
     * @param message 要添加的聊天消息
     */
    public void addMessage(ChatMessage message) {
        this.messages.add(message);
    }

    /**
     * 获取所有消息
     *
     * @return 消息列表，如果列表为空则返回空列表（防止返回 null）
     */
    public List<ChatMessage> getMessages() {
        if (messages == null)
            return new ArrayList<>();
        return messages;
    }

    /**
     * 获取用户发送的最后一条消息
     * <p>
     * 通过倒序遍历消息列表查找。
     * </p>
     *
     * @return 最后一条用户消息，如果未找到则返回 null
     */
    public ChatMessage getLastUserMessage() {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).getSender() == ChatMessage.Sender.USER) {
                return messages.get(i);
            }
        }
        return null;
    }

    /**
     * 获取 AI 回复的最后一条消息
     * <p>
     * 通过倒序遍历消息列表查找。
     * </p>
     *
     * @return 最后一条 AI 消息，如果未找到则返回 null
     */
    public ChatMessage getLastAssistantMessage() {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).getSender() == ChatMessage.Sender.ASSISTANT) {
                return messages.get(i);
            }
        }
        return null;
    }

    // Getter & Setter 方法

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
     * 获取会话开始时间
     * @return 开始时间
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * 设置会话开始时间
     * @param startTime 开始时间
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * 设置消息列表
     * @param messages 消息列表
     */
    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
