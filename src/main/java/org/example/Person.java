package org.example;

/**
 * 人员实体类
 * <p>
 * 这是一个简单的 POJO (Plain Old Java Object)，主要用于测试 JSON 序列化和反序列化功能。
 * </p>
 */
public class Person {
    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private int age;

    /**
     * 无参构造函数
     * <p>
     * Jackson 等 JSON 库在反序列化时通常需要无参构造函数。
     * </p>
     */
    public Person() {}

    /**
     * 全参构造函数
     *
     * @param name 姓名
     * @param age  年龄
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取年龄
     *
     * @return 年龄
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 重写 toString 方法
     *
     * @return 对象的字符串表示
     */
    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
