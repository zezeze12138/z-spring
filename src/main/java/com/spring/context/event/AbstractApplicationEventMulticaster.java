package com.spring.context.event;

import com.spring.context.ApplicationListener;

/**
 * 抽象的应用事件多播器
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster{

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {

    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {

    }
}
