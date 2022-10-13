package com.spring.beans.factory.support;

import com.spring.beans.BeanWrapper;
import com.spring.beans.factory.ObjectFactory;
import com.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.security.AccessController;
import java.security.PrivilegedAction;
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
        Object exposedObject = bean;
        try{
            populateBean(beanName, mbd, instanceWrapper);
            exposedObject = initializedBean(beanName, exposedObject, mbd);
        }catch (Exception e){
            throw new RuntimeException("初始化bean失败");
        }
        return null;
    }

    private Object initializedBean(String beanName, final Object bean, RootBeanDefinition mbd) {
        if(System.getSecurityManager() != null){
            AccessController.doPrivileged((PrivilegedAction<Object>) () ->{
                invokeAwareMethods(beanName, bean);
                return null;
            }, getAccessControlContext());
        }else {
            invokeAwareMethods(beanName, bean);
        }
        Object wrappedBean = bean;
        if(mbd == null || !mbd.isSynthetic()){
            wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
        }
        try{
            invokeInitMethods(beanName, wrappedBean, mbd);
        }catch (Exception e){
            throw new RuntimeException("调用初始化方法失败");
        }
        if(mbd == null || !mbd.isSynthetic()){
            wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        }
        return wrappedBean;
    }

    private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessAfterInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    private void invokeInitMethods(String beanName, Object wrappedBean, RootBeanDefinition mbd) {

    }

    protected  Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName){
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()) {
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if(current == null){
                return result;
            }
            result = current;
        }
        return result;
    }

    private void invokeAwareMethods(final String beanName, final Object bean) {

    }

    private void populateBean(String beanName, RootBeanDefinition mbd, BeanWrapper instanceWrapper) {
        if(instanceWrapper == null){
            if(mbd.hasPropertyValues()){
                throw new RuntimeException("空实例不能获取到属性值");
            }else {
                return;
            }
        }
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
