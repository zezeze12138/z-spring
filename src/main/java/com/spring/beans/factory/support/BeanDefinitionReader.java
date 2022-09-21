package com.spring.beans.factory.support;

import com.spring.core.io.Resource;
import com.spring.core.io.ResourceLoader;

/**
 * Bean定义读取器
 */
public interface BeanDefinitionReader {

    /**
     * 获取Bean定义注册器
     * @return
     */
    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    /**
     * 加载Bean定义
     * @param resource
     * @return
     */
    int loadBeanDefintions(Resource resource);

    /**
     * 加载Bean定义
     * @param resources
     * @return
     */
    int loadBeanDefinitions(Resource... resources);

    int loadBeanDefinitions(String location);

    int loadBeanDefinitions(String... location);
}
