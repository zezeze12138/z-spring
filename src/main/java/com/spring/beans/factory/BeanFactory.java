package com.spring.beans.factory;

/**
 * bean工厂接口
 */
public interface BeanFactory {

    /**
     * 前缀
     */
    String FACTORY_BEAN_PREFIX = "&";

    /**
     * 获取bean
     * @param name
     * @return
     */
    Object getBean(String name);

    <T> T getBean(String name, Class<T> requiredType);

    Object getBean(String name, Object... args);

    <T> T getBean(Class<T> requiredType, Object... args);

    // TODO: 2022/8/14 下面还有其他获取bean的方法

    boolean containsBean(String name);
}
