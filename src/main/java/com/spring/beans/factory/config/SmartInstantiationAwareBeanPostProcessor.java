package com.spring.beans.factory.config;

public interface SmartInstantiationAwareBeanPostProcessor {

    default Object getEarlyBeanReference(Object bean, String beanName){
        return bean;
    }

}
