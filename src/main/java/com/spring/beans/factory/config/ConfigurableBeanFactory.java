package com.spring.beans.factory.config;

import com.spring.beans.factory.HierarchicalBeanFactory;
import com.spring.core.convert.ConversionService;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry{

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

    /**
     * 设置属性填充转换服务
     * @param conversionService
     */
    void setConversionService(ConversionService conversionService);

    /**
     * 设置临时类加载器
     * @param tempClassLoader
     */
    void setTempClassLoader(ClassLoader tempClassLoader);

    ClassLoader getBeanClassLoader();


}
