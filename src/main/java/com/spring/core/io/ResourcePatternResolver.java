package com.spring.core.io;


public interface ResourcePatternResolver extends ResourceLoader {

    String CLASSPATH_ALL_URL_PREFIX = "classpath*:";


    Resource[] getResources(String locationPattern);

}
