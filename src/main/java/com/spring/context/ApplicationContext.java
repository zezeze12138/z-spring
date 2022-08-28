package com.spring.context;

import com.spring.core.env.EnvironmentCapable;

/**
 * 应用上下文接口
 */
public interface ApplicationContext extends EnvironmentCapable, ApplicationEventPublisher {

    String getId();

    String getApplciationName();

    String getDisplayName();

    long getStartupDate();

    ApplicationContext getParent();


    //AutowireCapableBeanFactory getAutowireCapableBeanFactory() throws IllegalStateException;

}
