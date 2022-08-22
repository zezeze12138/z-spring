package com.spring.core.env;

/**
 * 配置参数解析器
 */
public interface ConfigurablePropertyResolver extends PropertyResolver{

    void setRequiredProperties(String... requiredProperties);

    void validateRequiredProperties();
}
