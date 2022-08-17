package com.spring.core.io;

public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源
     * @param location
     * @return
     */
    Resource getResource(String location);

    /**
     * 获取类加载器
     * @return
     */
    ClassLoader getClassLoader();

}
