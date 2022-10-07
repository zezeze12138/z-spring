package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableBeanFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory{

    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

    private final Set<String> alreadyCreated = Collections.newSetFromMap(new ConcurrentHashMap<>(256));

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

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
}
