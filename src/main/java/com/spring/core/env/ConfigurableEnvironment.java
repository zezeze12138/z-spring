package com.spring.core.env;

import java.util.Map;

public interface ConfigurableEnvironment extends Environment{

    void setActiveProfiles(String... profiles);

    void setDefaultProfiles(String... profiles);

    Map<String, Object> getSystemProperties();

    Map<String, Object> getSystemEnvironment();

    void merge(ConfigurableEnvironment parent);
}
