package com.spring.beans.factory.support;

import com.spring.beans.BeanMetadataAttributeAccessor;
import com.spring.beans.factory.config.BeanDefinition;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象的Bean定义
 */
public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition, Cloneable{

    private String scope = "";

    private boolean abstractFlag = false;

    private volatile Object beanClass;



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

    public AbstractBeanDefinition() {
        this(null, null);
    }

    // TODO: 2022/9/30 这里还要完善
    public AbstractBeanDefinition(Object o, Object o1) {
    }

    public void setAbstract(boolean abstractFlag) {
        this.abstractFlag = abstractFlag;
    }


    public abstract RootBeanDefinition cloneBeanDefinition();

    public void validate(){
        if(hasBeanClass()){
            prepareMethodOverrides();
        }
    }

    private void prepareMethodOverrides() {
        if(hasMethodOverrides()){

        }
    }

    protected boolean hasMethodOverrides(){
        return false;
    }



    public boolean hasBeanClass(){
        return (this.beanClass instanceof Class);
    }

    public void setBeanClass(Class<?> beanClass){
        this.beanClass = beanClass;
    }
}
