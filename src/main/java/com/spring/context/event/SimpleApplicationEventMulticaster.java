package com.spring.context.event;

import com.spring.beans.factory.BeanFactory;

/**
 * 简单的应用事件多播器
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
    }
}
