package com.spring.beans.factory.support;

import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.FactoryBean;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.core.convert.ConversionService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认列表的Bean工厂
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry, Serializable{

    private volatile List<String> beanDefinitionsNames = new ArrayList<>(256);

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition bd = this.beanDefinitionMap.get(beanName);
        if(bd == null){
            throw new RuntimeException("未找到bean定义");
        }
        return bd;
    }

    @Override
    public void ignoreDependencyInterface(Class<?> ifc) {

    }

    @Override
    public void registerResolvableDependency(Class<?> dependencyType, Object autowiredValue) {

    }

    @Override
    public void freezeConfiguration() {

    }

    @Override
    public void preInstantiateSingletons() {
        List<String> beanNames = new ArrayList<>(this.beanDefinitionsNames);
        for(String beanName : beanNames){
            Object bean = getBean("&"+ beanName);
            if(bean instanceof FactoryBean){
                final FactoryBean<?> factory = (FactoryBean<?>) bean;
                boolean isEagerInit = true;
                if(isEagerInit){
                    getBean(beanName);
                }
            }else{
                getBean(beanName);
            }
        }
        for (String beanName : beanNames) {
            Object singletonInstance = getSingleton(beanName);
        }
    }


    @Override
    public String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
        return new String[0];
    }

    @Override
    public <T> T createBean(Class<T> beanClass) {
        return null;
    }

    @Override
    public void setBeanClassLoader(ClassLoader beanClassLoader) {

    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {

    }

    @Override
    public void setConversionService(ConversionService conversionService) {

    }

    @Override
    public void setTempClassLoader(ClassLoader tempClassLoader) {

    }

    @Override
    public BeanFactory getParentBeanFactory() {
        return null;
    }

    @Override
    public boolean containsLocalBean(String name) {
        return false;
    }

    @Override
    public Object getBean(String name) {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) {
        return null;
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public Object getSingleton(String beanName) {
        return null;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {

    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public void registerAlias(String name, String alias) {

    }

    @Override
    public void removeAlias(String alias) {

    }

    @Override
    public boolean isAlias(String name) {
        return false;
    }

    @Override
    public String[] getAliases(String name) {
        return new String[0];
    }
}
