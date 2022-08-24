package com.spring.beans.factory.config;

public interface ConfigurableBeanFactory extends SingletonBeanRegistry{

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 设置bean的类加载器
     * @param beanClassLoader
     */
    void setBeanClassLoader(ClassLoader beanClassLoader);

    /**
     * 添加bean后置处理器
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
