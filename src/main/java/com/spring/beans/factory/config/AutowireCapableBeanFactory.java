package com.spring.beans.factory.config;

import com.spring.beans.factory.BeanFactory;

public interface AutowireCapableBeanFactory extends BeanFactory {

    <T> T createBean(Class<T> beanClass);



}
