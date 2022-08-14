package com.spring.core.env;

/**
 * 配置属性解析器接口
 */
public interface PropertyResolver {

    /**
     * 是否包含属性
     * @param key
     * @return
     */
    boolean containsProperty(String key);

    /**
     * 获取属性
     * @param key
     * @return
     */
    String getProperty(String key);

    String getProperty(String key, String defaultValue);

    // TODO: 2022/8/14 下面还有其他的获取属性方法
}
