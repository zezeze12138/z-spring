package com.spring.beans.factory.support;

import com.spring.beans.BeanUtils;
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
        private final RootBeanDefinition beanDefinition;
        private final BeanFactory owner;


        public CglibSubclassCreator(RootBeanDefinition bd, BeanFactory owner) {
            this.beanDefinition = bd;
            this.owner = owner;
        }

        public Object instantiate(Constructor<?> ctor, Object... args) {
            Class<?> subclass = createEnhancedSubcalss(this.beanDefinition);
            Object instance;
            if(ctor == null){
                instance = BeanUtils.instantiateClass(subclass);
            }
            return null;
        }

        private Class<?> createEnhancedSubcalss(RootBeanDefinition beanDefinition) {
            return null;
        }
    }
}
