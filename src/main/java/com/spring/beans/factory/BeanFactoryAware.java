package com.spring.beans.factory;

/**
 * Bean工厂Aware接口
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置Bean工厂
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory);

}
