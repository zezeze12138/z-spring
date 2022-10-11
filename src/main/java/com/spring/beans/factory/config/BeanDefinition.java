package com.spring.beans.factory.config;

import com.spring.beans.BeanMetadataElement;
import com.spring.beans.MutablePropertyValues;
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

    void setScope(String scope);

    String getScope();

    void setLazyInit(boolean lazyInit);

    boolean isLazyInit();

    boolean setDependsOn(String... dependsOn);

    String[] getDependsOn();

    void setAutowireCandidate(boolean autowireCandidate);

    boolean isAutowireCandidate();

    void setPrimary(boolean primary);

    boolean isPrimary();

    void setFactoryBeanName(String factoryBeanName);

    String getFactoryBeanName();

    void setFactoryMethodName(String factoryMethodName);

    String getFactoryMethodName();

    void setRole(int role);

    int getRole();

    boolean isSingleton();

    boolean isPrototype();

    boolean isAbstract();

    BeanDefinition getOriginatingBeanDefinition();

    MutablePropertyValues getPropertyValues();

    default boolean hasPropertyValues(){
        return !getPropertyValues().isEmpty();
    }
}
