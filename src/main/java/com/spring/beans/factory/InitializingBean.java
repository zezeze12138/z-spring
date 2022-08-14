package com.spring.beans.factory;

/**
 * 初始化Bean
 */
public interface InitializingBean {

    /**
     * 初始化Bean时的操作
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;

}
