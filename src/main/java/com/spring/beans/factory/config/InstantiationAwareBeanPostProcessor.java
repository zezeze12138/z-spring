package com.spring.beans.factory.config;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws Exception{
        return true;
    }

}
