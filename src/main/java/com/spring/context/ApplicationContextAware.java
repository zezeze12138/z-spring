package com.spring.context;

import com.spring.beans.factory.Aware;

/**
 * 应用上下文的Aware接口
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置应用上下文
     * @param applicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext);

}
