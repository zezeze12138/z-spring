package com.spring.beans.factory.support;

import com.spring.beans.factory.config.AutowireCapableBeanFactory;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


    @Override
    public <T> T createBean(Class<T> beanClass) {
        RootBeanDefinition bd = new RootBeanDefinition(beanClass);
        bd.setScope("prototype");

        return (T) createBean(beanClass.getName(), bd, null);
    }

    private Object createBean(String beanName, RootBeanDefinition mbd, Object[] args){
        RootBeanDefinition mbdToUse = mbd;
        return null;
    }
}
