package com.spring.beans.factory.support;

import com.spring.beans.factory.DisposableBean;
import com.spring.beans.factory.ObjectFactory;
import com.spring.beans.factory.config.SingletonBeanRegistry;
import com.spring.core.SimpleAliasRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

    /**
     * 存放已经构建好的单例bean
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 存放构建单例bean的工厂
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new ConcurrentHashMap<>(16);

    /**
     * 存放早期的单例bean，未完成属性注入的bean，用于解决循环依赖
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * 正在创建的bean集合
     */
    private final Set<String> singletonCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * 已注册完成的单例bean
     */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /**
     * 可销毁的beans
     */
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    /**
     * 包含bean名称之间的映射，bean名称到bean包含的bean名称集
     */
    private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

    /**
     * 依赖的bean名称之间映射，bean名称到依赖bean的名称集。
     */
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * 依赖的bean名称之间映射，bean名称到bean依赖项的bean名称集
     */
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects){
            Object oldObject = this.singletonObjects.get(beanName);
            if(oldObject == null){
                addSingleton(beanName, singletonObject);
            }
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects){
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonObjects.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    @Override
    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory){
        synchronized (this.singletonObjects){
            if(!this.singletonFactories.containsKey(beanName)){
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
                this.registeredSingletons.add(beanName);
            }
        }
    }

    /**
     * 获取单例，三级缓存解决Bean循环依赖问题
     * @param beanName
     * @param allowEarlyReference
     * @return
     */
    private Object getSingleton(String beanName, boolean allowEarlyReference) {
        //先在已经构建的缓存中获取bean
        Object singletonObject = this.singletonObjects.get(beanName);
        //是否bean未构建且正在构建中
        if(singletonObject == null && isSingletonCurrentlyInCreation(beanName)){
            synchronized (this.singletonObjects){
                //是否在早期bean缓存中存在bean
                singletonObject = this.earlySingletonObjects.get(beanName);
                //如果早期bean缓存中不存在bean且允许进行早期的Bean属性引用
                if(singletonObject == null && allowEarlyReference){
                    //获取构建bean的工厂
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    //如果构建bean的工厂不为空，则通过工厂创建bean
                    if(singletonFactory != null){
                        //通过工厂创建bean
                        singletonObject = singletonFactory.getObject();
                        //将bean放入早期缓存中
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        //将单例bean的创建工厂在集合移除
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    /**
     * 是否Bean在存在中
     * @param beanName
     * @return
     */
    public boolean isSingletonCurrentlyInCreation(String beanName){
        return this.singletonCurrentlyInCreation.contains(beanName);
    }

    /**
     * 销毁单例
     * @param beanName
     */
    public void destroySingleton(String beanName){
        removeSingleton(beanName);

        DisposableBean disposableBean;
        synchronized (this.disposableBeans){
            disposableBean = (DisposableBean) this.disposableBeans.remove(beanName);
        }
        destroyBean(beanName, disposableBean);
    }

    /**
     * 移除单例
     * @param beanName
     */
    public void removeSingleton(String beanName){
        synchronized (this.singletonObjects){
            this.singletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.remove(beanName);
        }
    }

    /**
     * 销毁Bean
     * @param beanName bean名称
     * @param bean 可销毁的bean
     */
    public void destroyBean(String beanName, DisposableBean bean){
        Set<String> dependencies;
        synchronized (this.dependentBeanMap){
            dependencies = this.dependentBeanMap.remove(beanName);
        }
        if(dependencies != null){
            for(String dependentBeanName : dependencies){
                destroySingleton(dependentBeanName);
            }
        }
        if(bean != null){
            try{
                bean.destroy();
            }catch (Exception e){

            }
        }
        Set<String> containedBeans;
        synchronized (this.containedBeanMap){
            containedBeans = this.containedBeanMap.remove(beanName);
        }
        if(containedBeans != null){
            for(String containedBeanName : containedBeans){
                destroySingleton(containedBeanName);
            }
        }
        synchronized (this.dependentBeanMap){
            for(Iterator<Map.Entry<String, Set<String>>> iterator = this.dependentBeanMap.entrySet().iterator(); iterator.hasNext();){
                Map.Entry<String, Set<String>> entry = iterator.next();
                Set<String> dependenciesToClean = entry.getValue();
                dependenciesToClean.remove(beanName);
                if(dependenciesToClean.isEmpty()){
                    iterator.remove();
                }
            }
        }
        this.dependenciesForBeanMap.remove(beanName);
    }
}
