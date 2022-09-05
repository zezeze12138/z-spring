package com.spring.beans.factory.support;

import com.spring.beans.factory.config.SingletonBeanRegistry;
import com.spring.core.SimpleAliasRegistry;

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {
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
