package com.spring.beans.factory.support;

import com.spring.beans.BeanUtils;
import com.spring.beans.factory.BeanFactory;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-05  20:43
 * @Version: 1.0
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    @Override
    public Object instantiate(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        if(!bd.hasMethodOverrides()){
            Constructor<?> constructorToUse;
            synchronized (bd.constructorArgumentLock){
                constructorToUse = (Constructor<?>) bd.resolvedConstructorOrFactoryMethod;
                if(constructorToUse == null){
                    final Class<?> clazz = bd.getBeanClass();
                    if(clazz.isInterface()){
                        throw new RuntimeException("该类是一个接口");
                    }
                    try{
                        if (System.getSecurityManager() != null) {
                            constructorToUse = AccessController.doPrivileged(
                                        (PrivilegedExceptionAction<Constructor<?>>) clazz::getDeclaredConstructor);

                        }else {
                            constructorToUse = clazz.getDeclaredConstructor();
                        }
                        bd.resolvedConstructorOrFactoryMethod = constructorToUse;
                    }catch (Throwable ex){
                        throw new RuntimeException("未发现默认的构造函数");
                    }
                }
            }
            return BeanUtils.instantiateClass(constructorToUse);
        }else {
            return instantiateWithMethodInjection(bd, beanName, owner);
        }
    }

    private Object instantiateWithMethodInjection(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        return null;
    }
}
