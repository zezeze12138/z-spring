package com.spring.beans.factory.support;

import com.spring.beans.factory.ObjectFactory;
import com.spring.beans.factory.config.SingletonBeanRegistry;
import com.spring.core.SimpleAliasRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /**
     * 存放已经构建好的单例bean
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 存放构建单例bean的工厂
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>(16);

    /**
     * 存放早期的单例bean，未完成属性注入的bean，用于解决循环依赖
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    /**
     * 获取单例，三级缓存解决Bean循环依赖问题
     * @param beanName
     * @param allowEarlyReference
     * @return
     */
    private Object getSingleton(String beanName, boolean allowEarlyReference) {
        return null;
    }
}
