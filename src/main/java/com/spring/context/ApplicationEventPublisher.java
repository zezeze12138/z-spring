package com.spring.context;

/**
 * 应用事件发布器
 */
@FunctionalInterface
public interface ApplicationEventPublisher {

    default void publishEvent(ApplicationEvent event){
        publishEvent(event);
    }

    void publishEvent(Object event);

}
