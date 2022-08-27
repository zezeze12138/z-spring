package com.spring.beans.factory;

/**
 *
 */
public interface ListableBeanFactory extends BeanFactory{

    String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit);

}
