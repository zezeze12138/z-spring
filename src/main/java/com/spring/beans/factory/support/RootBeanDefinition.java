package com.spring.beans.factory.support;

import com.spring.beans.MutablePropertyValues;
import com.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Executable;
import java.util.Set;

/**
 * 根bean定义
 */
public class RootBeanDefinition extends AbstractBeanDefinition{

    final Object postProcessingLock = new Object();

    boolean postProcessed = false;

    final Object constructorArgumentLock = new Object();

    private Set<String> externallyManagedInitMethods;

    Executable resolvedConstructorOrFactoryMethod;

    boolean constructorArgumentsResolved = false;

    public RootBeanDefinition(BeanDefinition original) {
        super(original);
    }

    public <T> RootBeanDefinition(Class<T> beanClass) {
        super();
        setBeanClass(beanClass);
    }

    @Override
    public RootBeanDefinition cloneBeanDefinition() {
        return null;
    }

    @Override
    public void setParentName(String parentName) {

    }

    @Override
    public String getParentName() {
        return null;
    }

    @Override
    public void setBeanClassName(String beanClassName) {

    }

    @Override
    public String getBeanClassName() {
        return null;
    }

    @Override
    public void setScope(String scope) {

    }

    @Override
    public String getScope() {
        return null;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {

    }

    @Override
    public boolean isLazyInit() {
        return false;
    }

    @Override
    public boolean setDependsOn(String... dependsOn) {
        return false;
    }

    @Override
    public String[] getDependsOn() {
        return new String[0];
    }

    @Override
    public void setAutowireCandidate(boolean autowireCandidate) {

    }

    @Override
    public boolean isAutowireCandidate() {
        return false;
    }

    @Override
    public void setPrimary(boolean primary) {

    }

    @Override
    public boolean isPrimary() {
        return false;
    }

    @Override
    public void setFactoryBeanName(String factoryBeanName) {

    }

    @Override
    public String getFactoryBeanName() {
        return null;
    }

    @Override
    public void setFactoryMethodName(String factoryMethodName) {

    }

    @Override
    public String getFactoryMethodName() {
        return null;
    }

    @Override
    public void setInitMehodName(String initMehodName) {

    }

    @Override
    public void setRole(int role) {

    }

    @Override
    public int getRole() {
        return 0;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }




    @Override
    public boolean hasPropertyValues() {
        return false;
    }

    public boolean isExternallyManagedInitMethod(String initMethod){
        synchronized (this.postProcessingLock){
            return (this.externallyManagedInitMethods != null &&
            this.externallyManagedInitMethods.contains(initMethod));
        }
    }
}
