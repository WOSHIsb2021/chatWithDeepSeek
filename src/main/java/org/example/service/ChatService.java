package org.example.service;

import org.example.api.DeepSeekApiClient;
import org.example.model.ChatMessage;
import org.example.model.Session;

import java.util.UUID;

/**
 * 聊天服务类
 * <p>
 * 负责管理聊天会话和处理消息发送逻辑，协调 Session 和 DeepSeekApiClient。
 * </p>
 */
public class ChatService {
    /** 当前活跃的会话 */
    private Session currentSession;
    /** API 客户端实例 */
    private DeepSeekApiClient apiClient;

    /**
     * 构造函数
     * <p>
     * 初始化 API 客户端并开启一个新的会话。
     * </p>
     */
    public ChatService() {
        this.apiClient = new DeepSeekApiClient();
        startNewSession();
    }

    /**
     * 开启一个新的会话
     * <p>
     * 生成一个新的 UUID 作为会话 ID，并创建新的 Session 对象。
     * </p>
     */
    public void startNewSession() {
        String sessionId = UUID.randomUUID().toString();
        this.currentSession = new Session(sessionId);
    }

    /**
     * 发送用户消息并获取 AI 回复
     *
     * @param userMessage 用户输入的消息内容
     * @return AI 的回复内容，如果发生错误则返回错误信息
     */
    public String sendMessage(String userMessage) {
        // 1. 创建用户消息并添加到会话
        ChatMessage userMsg = new ChatMessage(ChatMessage.Sender.USER, userMessage, currentSession.getSessionId());
        currentSession.addMessage(userMsg);

        try {
            // 2. 调用 API 获取回复，传入当前会话的所有历史消息作为上下文
            String aiResponse = apiClient.chat(currentSession.getMessages());

            // 3. 创建 AI 消息并添加到会话
            ChatMessage aiMsg = new ChatMessage(ChatMessage.Sender.ASSISTANT, aiResponse, currentSession.getSessionId());
            currentSession.addMessage(aiMsg);

            return aiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 获取当前会话对象
     *
     * @return 当前 Session 对象
     */
    public Session getCurrentSession() {
        return currentSession;
    }
}
