package com.spring.beans.factory.support;

import com.spring.core.io.Resource;

/**
 * Bean定义读取器
 */
public interface BeanDefinitionReader {

    /**
     * 获取Bean定义注册器
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 加载Bean定义
     * @param resource
     * @return
     */
    int loadBeanDefintions(Resource resource);
}
