package com.spring.beans.factory.support;

import com.spring.beans.BeanMetadataAttributeAccessor;
import com.spring.beans.factory.config.BeanDefinition;

/**
 * 抽象的Bean定义
 */
public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition, Cloneable{

    private String scope = "";

    private boolean abstractFlag = false;

    public AbstractBeanDefinition(BeanDefinition original) {
        setParentName(original.getParentName());
        setBeanClassName(original.getBeanClassName());
        setScope(original.getScope());
        setAbstract(original.isAbstract());
        setLazyInit(original.isLazyInit());
        setFactoryBeanName(original.getFactoryBeanName());
        setFactoryMethodName(original.getFactoryMethodName());
        setRole(original.getRole());
        setSource(original.getSource());
        copyAttributesFrom(original);

    }

    public void setAbstract(boolean abstractFlag) {
        this.abstractFlag = abstractFlag;
    }


    public abstract RootBeanDefinition cloneBeanDefinition();
}
