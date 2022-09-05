package com.spring.beans.factory.support;

import com.spring.beans.factory.config.ConfigurableBeanFactory;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory{

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return null;
    }

    protected <T> T doGetBean(final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly){
        String beanNanme = transformedBeanName(name);
        Object sharedInstance = getSingleton(beanNanme);
        return null;
    }

    protected String transformedBeanName(String name){
        return null;
    }
}
