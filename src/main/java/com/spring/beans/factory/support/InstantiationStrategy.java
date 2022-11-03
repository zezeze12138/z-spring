package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;

public interface InstantiationStrategy {

    Object instantiate(RootBeanDefinition bd,  String beanName, BeanFactory owner);

}
