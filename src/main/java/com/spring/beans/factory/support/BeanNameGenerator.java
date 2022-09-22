package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

/**
 * bean名称生成接口
 */
public interface BeanNameGenerator {

    /**
     * 生成bean名称
     * @param definition
     * @param registry
     * @return
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}
