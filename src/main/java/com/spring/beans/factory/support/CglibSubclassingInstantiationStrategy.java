package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-05  20:38
 * @Version: 1.0
 */
public class CglibSubclassingInstantiationStrategy extends SimpleInstantiationStrategy{

    @Override
    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        return instantiateWithMethodInjection(bd, beanName, owner, null);
    }

    @Override
    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, String beanName, BeanFactory owner, Constructor<?> ctor, Object... args) {

        return new CglibSubclassCreator(bd, owner).instantiate(ctor, args);
    }

    private class CglibSubclassCreator {
        public CglibSubclassCreator(RootBeanDefinition bd, BeanFactory owner) {
        }

        public Object instantiate(Constructor<?> ctor, Object... args) {
            return null;
        }
    }
}
