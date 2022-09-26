package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanPostProcessor;

public interface MergedBeanDefinitionPostProcessor extends BeanPostProcessor {

    void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName);

    default void resetBeanDefinition(String beanName){

    }

}
