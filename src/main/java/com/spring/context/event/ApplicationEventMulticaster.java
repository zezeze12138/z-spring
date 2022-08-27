package com.spring.context.event;

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

    /**
     * 移除应用事件监听器
     * @param listener
     */
    void removeApplicationListener(ApplicationListener<?> listener);
}
