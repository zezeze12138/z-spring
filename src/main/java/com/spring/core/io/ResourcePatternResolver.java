package com.spring.core.io;

/**
 * 资源模式解析器
 */
public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


    Resource[] getResources(String locationPattern);

}
