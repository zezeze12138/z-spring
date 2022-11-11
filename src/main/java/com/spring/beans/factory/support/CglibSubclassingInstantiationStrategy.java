package com.spring.beans.factory.support;

import com.spring.beans.BeanUtils;
import com.spring.beans.factory.BeanFactory;
import com.spring.cglib.proxy.Callback;
import com.spring.cglib.proxy.Factory;
import com.spring.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-05  20:38
 * @Version: 1.0
 */
public class CglibSubclassingInstantiationStrategy extends SimpleInstantiationStrategy{

    @Override
    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, String beanName, BeanFactory owner) {
        return instantiateWithMethodInjection(bd, beanName, owner, null);
    }

    @Override
    protected Object instantiateWithMethodInjection(RootBeanDefinition bd, String beanName, BeanFactory owner, Constructor<?> ctor, Object... args) {

        return new CglibSubclassCreator(bd, owner).instantiate(ctor, args);
    }

    private class CglibSubclassCreator {
        private final RootBeanDefinition beanDefinition;
        private final BeanFactory owner;


        public CglibSubclassCreator(RootBeanDefinition bd, BeanFactory owner) {
            this.beanDefinition = bd;
            this.owner = owner;
        }

        public Object instantiate(Constructor<?> ctor, Object... args) {
            Class<?> subclass = createEnhancedSubcalss(this.beanDefinition);
            Object instance;
            if(ctor == null){
                instance = BeanUtils.instantiateClass(subclass);
            }else {
                try{
                    Constructor<?> enhancedSubcalssConstructor = subclass.getConstructor(ctor.getParameterTypes());
                    instance = enhancedSubcalssConstructor.newInstance(args);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("未能调用CGLIB增强子类的构造函数");
                }
            }
            Factory factory = (Factory) instance;
            factory.setCallbacks(new Callback[]{
                    NoOp.INSTANCE,
                    new LookupOverrideMethodInterceptor(this.beanDefinition, this.owner),
                    new ReplaceOverrideMethodInterceptor(this.beanDefinition, this.owner)
            });
            return instance;
        }

        private Class<?> createEnhancedSubcalss(RootBeanDefinition beanDefinition) {
            return null;
        }

        private class LookupOverrideMethodInterceptor implements Callback {
            public LookupOverrideMethodInterceptor(RootBeanDefinition beanDefinition, BeanFactory owner) {

            }
        }

        private class ReplaceOverrideMethodInterceptor implements Callback {
            public ReplaceOverrideMethodInterceptor(RootBeanDefinition beanDefinition, BeanFactory owner) {

            }
        }
    }
}
