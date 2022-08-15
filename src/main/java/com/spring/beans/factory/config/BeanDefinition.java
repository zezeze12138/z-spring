package com.spring.beans.factory.config;

import com.spring.beans.BeanMetadataElement;
import com.spring.core.AttributeAccessor;
import sun.reflect.ConstructorAccessor;

public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    int ROLE_APPLICATION = 0;

    int ROLE_SUPPORT = 1;

    int ROLE_INFRASTRUCTURE  =2;

    void setParentName(String parentName);

    String getParentName();

    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void getScope();

    void setLazyInit(boolean lazyInit);

    boolean setDependsOn(String... dependsOn);

    String[] getDependsOn();

    void setAutowireCandidate(boolean autowireCandidate);

    boolean isAutowireCandidate();

    void setPrimary(boolean primary);

    boolean isPrimary();

    void setFactoryBeanName(String factoryBeanName);

    String getFactoryBeanName();

    boolean isSingleton();

    boolean isPrototype();

    boolean isAbstract();

    BeanDefinition getOriginatingBeanDefinition();

}
