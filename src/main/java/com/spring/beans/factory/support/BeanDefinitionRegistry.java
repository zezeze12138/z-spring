package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.core.AliasRegistry;

/**
 * bean定义注册
 */
public interface BeanDefinitionRegistry extends AliasRegistry{

    /**
     * 注册Bean定义
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 移除Bean定义
     * @param beanName
     */
    void removeBeanDefinition(String beanName);

    /**
     * 是否包含Bean定义
     * @param beanName
     * @return
     */
    boolean containBeanDefintion(String beanName);
}
