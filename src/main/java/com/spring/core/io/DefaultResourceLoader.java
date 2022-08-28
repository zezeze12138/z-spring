package com.spring.core.io;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的资源加载器
 */
public class DefaultResourceLoader implements ResourceLoader {

    private ClassLoader classLoader;

    private final Map<Class<?>, Map<Resource, ?>> resourceCaches = new ConcurrentHashMap<>(4);

    public DefaultResourceLoader(ClassLoader classLoader) {
        this.classLoader = Thread.currentThread().getContextClassLoader();
    }

    @Override
    public Resource getResource(String location) {
        // TODO: 2022/8/17 通过路径获取资源文件
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * 清除资源缓存
     */
    public void clearResourceCaches(){
        this.resourceCaches.clear();
    }
}
