package com.spring.context.event;

import com.spring.context.ApplicationEvent;
import com.spring.context.ApplicationListener;

/**
 * 应用事件多播器接口
 */
public interface ApplicationEventMulticaster {

    /**
     * 增加应用事件监听器
     * @param listener
     */
    void addApplicationListener(ApplicationListener<?> listener);

    void addApplicationListenerBean(String listenerBeanName);

    /**
     * 移除应用事件监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * @param event
     */
    void multicastEvent(ApplicationEvent event);
}
