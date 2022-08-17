package com.spring.context;

import com.spring.core.env.ConfigurableEnvironment;

import java.io.Closeable;

public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

    String ENVIRONMENT_BEAN_NAME = "environment";

    String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

    String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";

    void setId(String id);

    void setParent(ApplicationContext parent);

    void setEnvironment(ConfigurableEnvironment environment);

    ConfigurableEnvironment getEnvironment();

    void refresh();



}
