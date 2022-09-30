package com.spring.beans.factory.support;

import com.spring.beans.BeanMetadataAttributeAccessor;
import com.spring.beans.MutablePropertyValues;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConstructorArgumentValues;

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

    private ConstructorArgumentValues constructorArgumentValues;

    private MutablePropertyValues propertyValues;


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

    public AbstractBeanDefinition(ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
        this.constructorArgumentValues = cargs;
        this.propertyValues = pvs;
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
