package com.spring.beans.factory.support;

import com.spring.beans.BeanWrapper;
import com.spring.beans.factory.DisposableBean;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;
import com.spring.beans.factory.config.Scope;
import com.spring.core.DecoratingClassLoader;
import com.spring.util.ClassUtils;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory{

    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    private final Map<String, Scope> scopes = new LinkedHashMap<>(8);

    private volatile boolean hasInstantiationAwareBeanPostProcessors;

    private volatile boolean hasDestructionAwareBeanPostProcessors;

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return null;
    }

    protected <T> T doGetBean(final String name, final Class<T> requiredType, final Object[] args, boolean typeCheckOnly){
        String beanNanme = transformedBeanName(name);
        Object sharedInstance = getSingleton(beanNanme);
        return null;
    }

    protected String transformedBeanName(String name){
        return null;
    }

    protected RootBeanDefinition getMergedLocalBeanDefinition(String beanName){
        RootBeanDefinition mbd = this.mergedBeanDefinitions.get(beanName);
        if(mbd != null){
            return mbd;
        }
        return getMergedBeanDefinition(beanName, getBeanDefinition(beanName),null);
    }

    protected RootBeanDefinition getMergedBeanDefinition(String beanName, BeanDefinition bd, BeanDefinition containingBd){
        synchronized (this.mergedBeanDefinitions){
            RootBeanDefinition mbd = null;
            if(containingBd == null){
                mbd = this.mergedBeanDefinitions.get(beanName);
            }

            if(mbd == null){
                if(bd.getParentName() == null){
                    if(bd instanceof RootBeanDefinition){
                        mbd = ((RootBeanDefinition) bd).cloneBeanDefinition();
                    }else{
                        mbd = new RootBeanDefinition(bd);
                    }
                }
            }
            return mbd;
        }
    }

    protected Class<?> resolveBeanClass(final RootBeanDefinition mbd, String beanName, final Class<?>... typesToMatch){
        try {
            if(mbd.hasBeanClass()){
                return mbd.getBeanClass();
            }
            if (System.getSecurityManager() != null) {
                return AccessController.doPrivileged((PrivilegedExceptionAction<Class<?>>) () ->
                        doResolveBeanClass(mbd, typesToMatch), getAccessControlContext());
            }
            else {
                return doResolveBeanClass(mbd, typesToMatch);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private Class<?> doResolveBeanClass(RootBeanDefinition mbd, Class<?>[] typesToMatch) throws ClassNotFoundException {
        ClassLoader beanClassLoader = getBeanClassLoader();
        ClassLoader dynamicLoader = beanClassLoader;
        boolean freshResolve = false;

        if (!(typesToMatch == null || typesToMatch.length == 0)) {
            ClassLoader tempClassLoader = getTempClassLoader();
            if (tempClassLoader != null) {
                dynamicLoader = tempClassLoader;
                freshResolve = true;
                if (tempClassLoader instanceof DecoratingClassLoader) {
                    DecoratingClassLoader dcl = (DecoratingClassLoader) tempClassLoader;
                    for (Class<?> typeToMatch : typesToMatch) {
                        dcl.excludeClass(typeToMatch.getName());
                    }
                }
            }
        }
        String className = mbd.getBeanClassName();
        if (className != null) {
            Object evaluated = evaluateBeanDefinitionString(className, mbd);
            if (!className.equals(evaluated)) {
                if (evaluated instanceof Class) {
                    return (Class<?>) evaluated;
                }
                else if (evaluated instanceof String) {
                    className = (String) evaluated;
                    freshResolve = true;
                }
                else {
                    throw new RuntimeException("???????????????");
                }
            }
            if (freshResolve) {
                if (dynamicLoader != null) {
                    try {
                        return dynamicLoader.loadClass(className);
                    }
                    catch (ClassNotFoundException ex) {
                        throw new RuntimeException("?????????????????????");
                    }
                }
                try {
                    return ClassUtils.forName(className, dynamicLoader);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return mbd.resolveBeanClass(beanClassLoader);
    }

    private Object evaluateBeanDefinitionString(String className, RootBeanDefinition mbd) {
        return null;
    }

    private ClassLoader getTempClassLoader() {
        return null;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    public boolean hasBeanCreationStarted(){
        return !this.alreadyCreated.isEmpty();
    }

    public void clearMergedBeanDefinition(String beanName){
        this.mergedBeanDefinitions.remove(beanName);
    }

    public List<BeanPostProcessor> getBeanPostProcessors(){
        return this.beanPostProcessors;
    }

    protected boolean hasInstantiationAwareBeanPostProcessors() {
        return this.hasInstantiationAwareBeanPostProcessors;
    }

    protected boolean removeSingletonIfCreatedForTypeCheckOnly(String beanName) {
        if (!this.alreadyCreated.contains(beanName)) {
            removeSingleton(beanName);
            return true;
        }
        else {
            return false;
        }
    }

    protected  void registerDisposableBeanIfNecessary(String beanName, Object bean, RootBeanDefinition mbd){
        AccessControlContext acc = (System.getSecurityManager() != null ? getAccessControlContext() : null);
        if(!mbd.isPrototype() && requiresDestruction(bean, mbd)){
            if(mbd.isSingleton()){
                registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, mbd, getBeanPostProcessors(), acc));
            }else{
                Scope scope = this.scopes.get(mbd.getScope());
                if(scope == null){
                    throw new RuntimeException("???????????????????????????????????????");
                }
                scope.registerDestructionCallback(beanName, new DisposableBeanAdapter(bean, beanName, mbd, getBeanPostProcessors(), acc));
            }
        }
    }

    private boolean requiresDestruction(Object bean, RootBeanDefinition mbd) {
        return (bean.getClass() != NullBean.class &&
                (DisposableBeanAdapter.hasDestroyMethod(bean, mbd) || (hasDestructionAwareBeanPostProcessors() &&
                DisposableBeanAdapter.hasApplicableProcessors(bean, getBeanPostProcessors()))));
    }

    private boolean hasDestructionAwareBeanPostProcessors() {
        return this.hasDestructionAwareBeanPostProcessors;

    }

    protected void initBeanWrapper(BeanWrapper bw){

    }

    public ClassLoader getBeanClassLoader(){
        return this.beanClassLoader;
    }

    public boolean hashInstantiationAwareBeanPostProcessors(){
        return this.hasInstantiationAwareBeanPostProcessors;
    }
}
