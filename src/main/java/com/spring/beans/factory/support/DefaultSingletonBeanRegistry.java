package com.spring.beans.factory.support;

import com.spring.beans.factory.ObjectFactory;
import com.spring.beans.factory.config.SingletonBeanRegistry;
import com.spring.core.SimpleAliasRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {

    }

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
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
}
