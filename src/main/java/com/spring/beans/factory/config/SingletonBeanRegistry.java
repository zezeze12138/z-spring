package com.spring.beans.factory.config;

/**
 * 单例bean注册接口
 */
public interface SingletonBeanRegistry {

    /**
     * 注册单例
     * @param beanName
     * @param singletonObject
     */
    void registerSingleton(String beanName, Object singletonObject);

    /**
     * 通过beanName获取单例bean
     * @param beanName
     * @return
     */
    Object getSingleton(String beanName);
}
