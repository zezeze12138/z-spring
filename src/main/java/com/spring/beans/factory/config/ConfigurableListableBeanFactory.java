package com.spring.beans.factory.config;

import com.spring.beans.factory.ListableBeanFactory;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 添加被忽略的依赖接口，例如Aware接口
     * @param ifc
     */
    void ignoreDependencyInterface(Class<?> ifc);

    /**
     * 注注册可以解析的自动装配，例如BeanFactory接口
     * @param dependencyType
     * @param autowiredValue
     */
    void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue);

    /**
     * 冻结配置
     */
    void freezeConfiguration();

    /**
     * 实例化单例方法
     */
    void preInstantiateSingletons();
}
