package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConfigurableBeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory{

    private final Map<String, RootBeanDefinition> mergedBeanDefinitions = new ConcurrentHashMap<>(256);

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

    protected abstract BeanDefinition getBeanDefinition(String beanName);
}
