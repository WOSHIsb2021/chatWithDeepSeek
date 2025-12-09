# DeepSeek Console Chat (stuDome2)

这是一个基于 Java 的控制台应用程序，演示了如何连接和使用 DeepSeek AI 的 API 进行对话。项目展示了基础的 HTTP 客户端封装、JSON 处理以及简单的会话管理。

## ✨ 功能特性

*   **控制台交互**：直接在命令行与 AI 进行实时对话。
*   **会话管理**：支持多轮对话上下文记忆（在单次运行周期内）。
*   **配置灵活**：通过配置文件管理 API Key、模型名称和参数。
*   **JSON 处理**：使用 Jackson 库进行高效的数据序列化与反序列化。

## 🛠️ 环境要求

*   **Java**: JDK 8 或更高版本
*   **Maven**: 3.6 或更高版本

## 🚀 快速开始

### 1. 配置 API Key

在运行之前，您需要配置 DeepSeek 的 API 密钥。

打开 `src/main/resources/config.properties-example` 文件复制一份为 `config.properties` ，填入你自己的配置：

```properties
# DeepSeek API Configuration
deepseek.api.key=sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx  <-- 替换为您的真实 API Key
deepseek.api.url=https://api.deepseek.com/v1/chat/completions
deepseek.model=deepseek-chat
```

### 2. 编译项目

在项目根目录下打开终端，运行以下命令下载依赖并编译：

```bash
mvn clean compile
```

### 3. 运行程序

使用 Maven 插件直接运行主程序：

```bash
mvn exec:java -Dexec.mainClass="org.example.ConsoleMain"
```

或者，如果您使用 IntelliJ IDEA 或 VS Code，可以直接打开 `src/main/java/org/example/ConsoleMain.java` 并点击运行按钮。

运行结果样例：
![运行结果截图](assets/image.png)

## 📂 项目结构

```text
src/main/java/org/example/
├── api/
│   └── DeepSeekApiClient.java  # HTTP 客户端，负责发送请求到 DeepSeek API
├── model/
│   ├── ChatMessage.java        # 聊天消息模型 (User/Assistant/System)
│   └── Session.java            # 会话模型，存储对话历史
├── service/
│   └── ChatService.java        # 业务逻辑层，协调 API 调用与会话状态
├── util/
│   ├── ConfigManager.java      # 单例模式读取 config.properties
│   └── JsonUtil.java           # Jackson 工具类封装
└── ConsoleMain.java            # 程序入口，包含控制台交互循环
```

## 📦 主要依赖

*   **Jackson Databind**: 用于 JSON 数据的序列化和反序列化。
*   **Jackson Datatype JSR310**: 处理 Java 8 的 `LocalDateTime` 日期格式。

## 📝 注意事项

*   本项目仅作为学习示例，API Key 存储在本地配置文件中，请勿将包含真实 Key 的配置文件提交到公共仓库。
*   控制台输出使用了简单的 `System.out.println`，在某些不支持 ANSI 转义序列的终端中可能显示效果有限。

---
Happy Coding!
