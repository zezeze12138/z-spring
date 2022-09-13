package com.spring.beans.factory.support;

import com.spring.beans.factory.config.AutowireCapableBeanFactory;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


    @Override
    public <T> T createBean(Class<T> beanClass) {
        return null;
    }
}
