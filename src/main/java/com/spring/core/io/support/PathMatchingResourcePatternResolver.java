package com.spring.core.io.support;

import com.spring.context.support.AbstractApplicationContext;
import com.spring.core.io.DefaultResourceLoader;
import com.spring.core.io.Resource;
import com.spring.core.io.ResourceLoader;
import com.spring.core.io.ResourcePatternResolver;

/**
 * 路径匹配资源模式解析器
 */
public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {

    private final ResourceLoader resourceLoader;

    public PathMatchingResourcePatternResolver() {
        this.resourceLoader = new DefaultResourceLoader();
    }

    public PathMatchingResourcePatternResolver(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Resource[] getResources(String locationPattern) {
        return new Resource[0];
    }

    @Override
    public Resource getResource(String location) {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
