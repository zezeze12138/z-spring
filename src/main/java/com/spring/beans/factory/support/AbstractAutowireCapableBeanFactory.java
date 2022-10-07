package com.spring.beans.factory.support;

import com.spring.beans.factory.config.AutowireCapableBeanFactory;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {


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
            Object beanInstance = doGreateBean(beanName, mbdToUse, args);
            return beanInstance;
        }catch (Exception e){
            throw new RuntimeException("bean创建期间出现意外异常");
        }
    }

    private Object doGreateBean(String beanName, RootBeanDefinition mbdToUse, Object[] args) {
        return null;
    }
}
