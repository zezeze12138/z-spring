package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-05  20:43
 * @Version: 1.0
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        return null;
    }
}
