package org.example.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * 配置管理器类
 * 
 * 单例模式实现，负责加载和提供 config.properties 中的配置信息。
 * 
 */
public class ConfigManager {
    /** 单例实例 */
    private static ConfigManager instance;
    /** 存储配置属性 */
    private Properties properties;

    /**
     * 私有构造函数
     * 
     * 防止外部实例化，初始化 Properties 并加载配置。
     * 
     */
    private ConfigManager() {
        properties = new Properties();
        loadConfig();
    }

    /**
     * 获取 ConfigManager 的单例实例
     * 
     * 使用双重检查锁 (Double-Checked Locking) 确保线程安全。
     * 
     *
     * @return ConfigManager 实例
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * 加载配置文件
     * 
     * 从 classpath 根目录加载 config.properties 文件。
     * 
     */
    private void loadConfig() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("找不到配置文件，使用默认配置");
                return;
            }
            properties.load(input);
            System.out.println("加载配置文件成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置属性值
     *
     * @param key 配置键
     * @return 配置值，如果键不存在则返回 null
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
