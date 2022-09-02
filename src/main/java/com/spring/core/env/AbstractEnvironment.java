package com.spring.core.env;

import java.util.Map;

/**
 * 抽象环境
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment {

    @Override
    public void setActiveProfiles(String... profiles) {

    }

    @Override
    public void setDefaultProfiles(String... profiles) {

    }

    @Override
    public Map<String, Object> getSystemProperties() {
        return null;
    }

    @Override
    public Map<String, Object> getSystemEnvironment() {
        return null;
    }

    @Override
    public void merge(ConfigurableEnvironment parent) {

    }
}
