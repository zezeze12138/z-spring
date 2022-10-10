package com.spring.beans.factory.support;

import com.spring.beans.BeanWrapper;
import com.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    @Override
    public <T> T createBean(Class<T> beanClass) {
        RootBeanDefinition bd = new RootBeanDefinition(beanClass);
        bd.setScope("prototype");

        return (T) createBean(beanClass.getName(), bd, null);
    }

    private Object createBean(String beanName, RootBeanDefinition mbd, Object[] args){
        RootBeanDefinition mbdToUse = mbd;
        Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
        if(resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null){
            mbdToUse = new RootBeanDefinition(mbd);
            mbdToUse.setBeanClass(resolvedClass);
        }
        try{
            mbdToUse.prepareMethodOverrides();
        }catch (Exception e){
            throw new RuntimeException("校验覆盖方法失败", e);
        }
        try{
            Object beanInstance = doCreateBean(beanName, mbdToUse, args);
            return beanInstance;
        }catch (Exception e){
            throw new RuntimeException("bean创建期间出现意外异常");
        }
    }

    private Object doCreateBean(String beanName, RootBeanDefinition mbd, Object[] args) {
        BeanWrapper instanceWrapper = null;
        if(mbd.isSingleton()){
            instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
        }
        if(instanceWrapper == null){
            instanceWrapper = createBeanInstance(beanName, mbd, args);
        }
        final Object bean = instanceWrapper.getWrappedInstance();
        Class<?> beanType = instanceWrapper.getWrappedClass();
        synchronized (mbd.postProcessingLock){
            if(!mbd.postProcessed){
                try{
                    applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
                }catch (Throwable ex){
                    throw new RuntimeException(ex);
                }
                mbd.postProcessed = true;
            }
        }
        boolean earlySingletonExposure = (mbd.isSingleton());
        if(earlySingletonExposure){
            addSingletonFactory(beanName, ()->getEarlyBeanReference(beanName, mbd, bean));
        }
        return null;
    }

    private Object getEarlyBeanReference(String beanName, RootBeanDefinition mbd, Object bean) {
        Object exposedObject = bean;
        if(!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors())
        for(BeanPostProcessor bp : getBeanPostProcessors()){
            if(bp instanceof SmartInstantiationAwareBeanPostProcessor){
                SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
                exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
            }
        }
        return exposedObject;
    }

    protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName){
        for(BeanPostProcessor bp : getBeanPostProcessors()){
            if(bp instanceof MergedBeanDefinitionPostProcessor){
                MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
                bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
            }
        }
    }

    private BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, Object[] args) {
        return null;
    }
}
