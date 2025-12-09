package org.example.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.model.ChatMessage;
import org.example.util.ConfigManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * DeepSeek API 客户端类
 * 
 * 该类负责与 DeepSeek API 进行 HTTP 通信，发送聊天消息并接收 AI 的回复。
 * 
 */
public class DeepSeekApiClient {
    /**
     * API 密钥
     */
    private final String apiKey;

    /**
     * API 请求 URL
     */
    private final String apiUrl;

    /**
     * 使用的模型名称
     */
    private final String model;

    /**
     * Jackson ObjectMapper 实例，用于 JSON 处理
     */
    private final ObjectMapper objectMapper;

    /**
     * 构造函数
     * 
     * 初始化 API 客户端，从 ConfigManager 加载配置信息。
     * 
     */
    public DeepSeekApiClient() {
        ConfigManager config = ConfigManager.getInstance();
        this.apiKey = config.getProperty("deepseek.api.key");
        this.apiUrl = config.getProperty("deepseek.api.url");
        this.model = config.getProperty("deepseek.model");
        this.objectMapper = new ObjectMapper();
    }

    /**
     * 发送聊天请求到 DeepSeek API
     *
     * @param messages 聊天消息历史列表
     * @return AI 的回复内容
     * @throws Exception 如果网络请求失败或 JSON 解析出错
     */
    public String chat(List<ChatMessage> messages) throws Exception {
        // 创建 URL 对象
        URL url = new URL(apiUrl);
        // 打开 HTTP 连接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        // 设置请求头
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Bearer " + apiKey);
        conn.setDoOutput(true); // 允许输出请求体

        // 构建请求体
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", model);
        requestBody.put("temperature", 0.7); // 设置温度参数，控制回复的随机性

        // 构建 messages 数组
        ArrayNode messagesNode = requestBody.putArray("messages");
        for (ChatMessage msg : messages) {
            ObjectNode msgNode = messagesNode.addObject();
            String role = "user";
            // 映射内部 Sender 枚举到 API 要求的 role 字符串
            if (msg.getSender() == ChatMessage.Sender.ASSISTANT) {
                role = "assistant";
            } else if (msg.getSender() == ChatMessage.Sender.SYSTEM) {
                role = "system";
            }
            msgNode.put("role", role);
            msgNode.put("content", msg.getContent());
        }

        // 将请求体转换为 JSON 字符串
        String jsonInputString = objectMapper.writeValueAsString(requestBody);

        // 发送请求体数据
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 获取响应码
        int responseCode = conn.getResponseCode();
        // 如果响应码不是 200 OK，读取错误流并抛出异常
        if (responseCode != 200) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                throw new RuntimeException("API Request Failed: " + responseCode + " " + response.toString());
            }
        }

        // 读取成功响应流
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            // 解析响应
            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode choicesNode = rootNode.path("choices");
            // 提取 AI 回复的内容
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                return messageNode.path("content").asText();
            } else {
                return "Error: No response from AI";
            }
        }
    }
}
