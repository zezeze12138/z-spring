package com.spring.core.io.support;

import com.spring.core.io.Resource;
import com.spring.core.io.ResourcePatternResolver;

public class PathMatchingResourcePatternResolver implements ResourcePatternResolver {

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
