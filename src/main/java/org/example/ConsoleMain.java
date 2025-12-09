package org.example;

import org.example.model.ChatMessage;
import org.example.service.ChatService;
import org.example.util.ConfigManager;
import org.example.util.JsonUtil;

import java.util.Scanner;

/**
 * 控制台主程序入口类
 * 
 * 该类包含了应用程序的 main 方法，负责启动程序、进行简单的功能测试以及运行交互式聊天循环。
 * 
 */
public class ConsoleMain {

    /**
     * 程序的入口点
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 1. 测试配置读取
        // 获取 ConfigManager 单例实例，并读取配置文件中的 deepseek.model 属性
        ConfigManager config = ConfigManager.getInstance();
        System.out.println("Current Model: " + config.getProperty("deepseek.model"));

        // 2. 测试消息模型
        // 创建一个用户发送的消息对象，用于测试 ChatMessage 类的功能
        ChatMessage msg = new ChatMessage(ChatMessage.Sender.USER, "你好", "001");
        // System.out.println(msg); // 测试 toString 方法输出

        // 3. 测试 JSON 转换 (测试用 POJO: Person)
        // 创建一个 Person 对象，用于测试 JsonUtil 的序列化和反序列化功能
        Person p = new Person("张三", 18);
        // 将 Person 对象转换为 JSON 字符串
        String json = JsonUtil.toJson(p);
        // System.out.println(json);

        // 将 JSON 字符串反序列化为 Person 对象
        Person p2 = JsonUtil.fromJson(json, Person.class);
        // System.out.println(p2);

        // 4. 启动聊天
        System.out.println("=== DeepSeek Chat Demo (Type 'exit' to quit) ===");
        
        // 初始化聊天服务
        ChatService chatService = new ChatService();
        Scanner scanner = new Scanner(System.in);

        // 进入交互式聊天循环
        while (true) {
            System.out.print("You: ");
            // 读取用户输入
            String input = scanner.nextLine();
            
            // 检查退出命令
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Goodbye!");
                break;
            }
            
            // 忽略空输入
            if (input.trim().isEmpty()) {
                continue;
            }
            
            System.out.print("AI is thinking...");
            // 发送消息给 AI 并获取回复
            String response = chatService.sendMessage(input);
            
            // 清除 "AI is thinking..." 提示 (使用回车符 \r 回到行首覆盖输出)
            System.out.println("\rAI: " + response);
        }
        // 关闭 Scanner 资源
        scanner.close();
    }
}