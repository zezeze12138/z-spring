package com.spring.beans.factory.support;

import com.spring.beans.BeanWrapper;
import com.spring.beans.BeanWrapperImpl;
import com.spring.beans.factory.*;
import com.spring.beans.factory.config.AutowireCapableBeanFactory;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import com.spring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import com.spring.core.NameThreadLocal;
import com.sun.deploy.util.ReflectionUtil;
import com.sun.xml.internal.ws.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private final ConcurrentMap<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    private boolean allowRawInjectionDespiteWrapping = false;

    protected final Log logger = LogFactory.getLog(getClass());

    private final NameThreadLocal<String> currentlyCreateBean = new NameThreadLocal<>("Currently created bean");

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
        if(earlySingletonExposure){
            Object earlySingletonReference = getSingleton(beanName);
            if(earlySingletonReference != null){
                if(exposedObject == bean){
                    exposedObject = earlySingletonExposure;
                }else if(!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)){
                    String[] dependentBeans = getDependentBeans(beanName);
                    Set<String> actualDependentBeans = new LinkedHashSet<>(dependentBeans.length);
                    for (String dependentBean : dependentBeans) {
                        if(!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)){
                            actualDependentBeans.add(dependentBean);
                        }
                    }
                    if(!actualDependentBeans.isEmpty()){
                        throw new RuntimeException("该bean名称已经注册到其他bean了");
                    }

                }

            }
        }
        try{
            registerDisposableBeanIfNecessary(beanName, bean, mbd);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return exposedObject;
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

    private void invokeInitMethods(String beanName, final Object bean, RootBeanDefinition mbd) {
        boolean isInitializingBean = (bean instanceof InitializingBean);
        if(isInitializingBean && (mbd == null || !mbd.isExternallyManagedInitMethod("afterPropertiesSet"))){
            if(System.getSecurityManager() != null){
                try {
                    AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () ->{
                        ((InitializingBean) bean).afterPropertiesSet();
                        return null;
                    }, getAccessControlContext());
                } catch (PrivilegedActionException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    ((InitializingBean) bean).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("初始化bean,afterPropertiesSet方法执行异常");
                }
            }
        }
        if(mbd != null && bean.getClass() != NullBean.class){
            String initMethodName = mbd.getInitMethodName();
            if((initMethodName != null && !initMethodName.isEmpty()) &&
            !(isInitializingBean && "afterPropertiesSet".equals(initMethodName)) &&
            !mbd.isExternallyManagedInitMethod(initMethodName)){

                invokeCustomInitMethod(beanName, bean, mbd);
            }
        }
    }

    private void invokeCustomInitMethod(String beanName, Object bean, RootBeanDefinition mbd) {
        String initMethodName = mbd.getInitMethodName();
        try {
            Method initMethod = (mbd.isNonPublicAccessAllowed() ?
                    beanName.getClass().getMethod(initMethodName, null) :
                    getMethodIfAvailable(bean.getClass(), initMethodName));
            if(initMethod == null){
                if(mbd.isEnforceInitMethod()){
                    throw new RuntimeException("未发现初始化方法名称");
                }else {
                    return;
                }
            }
            Method methodToInvoke = getInterfaceMethodIfPossible(initMethod);
            if(System.getSecurityManager() != null){

                AccessController.doPrivileged((PrivilegedAction<Object>) () ->{
                    if ((!Modifier.isPublic(methodToInvoke.getModifiers()) ||
                            !Modifier.isPublic(methodToInvoke.getDeclaringClass().getModifiers())) && !methodToInvoke.isAccessible()) {
                        methodToInvoke.setAccessible(true);
                    }
                    return null;
                }, getAccessControlContext());

                try {
                    AccessController.doPrivileged((PrivilegedExceptionAction<Object>) () ->
                        methodToInvoke.invoke(bean), getAccessControlContext());
                } catch (PrivilegedActionException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }else{
                try{
                    if ((!Modifier.isPublic(methodToInvoke.getModifiers()) ||
                            !Modifier.isPublic(methodToInvoke.getDeclaringClass().getModifiers())) && !methodToInvoke.isAccessible()) {
                        methodToInvoke.setAccessible(true);
                    }
                    methodToInvoke.invoke(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }



    }

    private Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes){
        if(paramTypes != null){
            try{
                return clazz.getMethod(methodName, paramTypes);
            } catch (NoSuchMethodException e) {
                return null;
            }
        }else{
            Set<Method> candidates = new HashSet<>(1);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if(methodName.equals(method.getName())){
                    candidates.add(method);
                }
            }
            if(candidates.size() == 1){
                return candidates.iterator().next();
            }
            return null;
        }
    }

    private Method getInterfaceMethodIfPossible(Method method){
        if(!Modifier.isPublic(method.getModifiers()) || method.getDeclaringClass().isInterface()){
            return method;
        }
        Class<?> current = method.getDeclaringClass();
        while(current != null && current != Object.class){
            Class<?>[] ifcs = current.getInterfaces();
            for (Class<?> ifc : ifcs) {
                try{
                    return ifc.getMethod(method.getName(), method.getParameterTypes());
                }catch (NoSuchMethodException e){

                }

            }
            current = current.getSuperclass();
        }
        return method;
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
        if(bean instanceof Aware){
            if(bean instanceof BeanNameAware){
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if(bean instanceof BeanClassLoaderAware){
                ClassLoader bcl = getBeanClassLoader();
                if(bcl != null){
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                }
            }
            if(bean instanceof BeanFactoryAware){
                ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
            }
        }
    }

    private void populateBean(String beanName, RootBeanDefinition mbd, BeanWrapper instanceWrapper) throws Exception {
        if(instanceWrapper == null){
            if(mbd.hasPropertyValues()){
                throw new RuntimeException("空实例不能获取到属性值");
            }else {
                return;
            }
        }
        if(!mbd.isSynthetic() && hashInstantiationAwareBeanPostProcessors()){
            for(BeanPostProcessor bp : getBeanPostProcessors()){
                if(bp instanceof SmartInstantiationAwareBeanPostProcessor){
                    InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
                    if(!ibp.postProcessAfterInstantiation(instanceWrapper.getWrappedInstance(), beanName)){
                        return;
                    }
                }
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
        Class<?> beanClass = resolveBeanClass(mbd, beanName);

        Supplier<?> instanceSupplier = mbd.getInstanceSupplier();
        if(instanceSupplier != null){
            return obtainFromSupplier(instanceSupplier, beanName);
        }
        if(mbd.getFactoryMethodName() != null){
            return instantiateUsingFactoryMethod(beanName, mbd, args);
        }
        return null;
    }

    private BeanWrapper instantiateUsingFactoryMethod(String beanName, RootBeanDefinition mbd, Object[] args) {
        return new ConstructorResolver(this).instantiateUsingFactoryMethod(beanName, mbd, args);
    }

    private BeanWrapper obtainFromSupplier(Supplier<?> instanceSupplier, String beanName) {
        Object instance;

        String outerBean = this.currentlyCreateBean.get();
        this.currentlyCreateBean.set(beanName);
        try{
            instance = instanceSupplier.get();
        }finally {
            if(outerBean != null){
                this.currentlyCreateBean.set(outerBean);
            }else{
                this.currentlyCreateBean.remove();
            }
        }
        if(instance == null){
            instance = new NullBean();
        }
        BeanWrapper bw = new BeanWrapperImpl(instance);
        initBeanWrapper(bw);
        return bw;
    }

    Log getLogger() {
        return logger;
    }
}
