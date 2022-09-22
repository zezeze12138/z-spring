package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

public abstract class BeanDefinitionReaderUtils {

    public static String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry){
        return generateBeanName(beanDefinition, registry, false);
    }

    public static String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean){
        String id = "";
        return id;
    }
}
