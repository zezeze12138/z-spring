package com.spring.beans.factory.support;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry{

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

    protected AccessControlContext getAccessControlContext() {
        return AccessController.getContext();
    }

}
