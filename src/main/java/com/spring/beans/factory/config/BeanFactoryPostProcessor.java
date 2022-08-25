package com.spring.beans.factory.config;

/**
 * Bean工厂的后置处理器接口
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);

}
