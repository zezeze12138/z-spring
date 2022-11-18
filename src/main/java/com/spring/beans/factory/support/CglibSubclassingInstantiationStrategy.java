package com.spring.beans.factory.support;

import com.spring.beans.BeanUtils;
import com.spring.beans.factory.BeanFactory;
import com.spring.cglib.proxy.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

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
            Enhancer enhancer = new Enhancer();
            return null;
        }

        private class LookupOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

            private final BeanFactory owner;

            public LookupOverrideMethodInterceptor(RootBeanDefinition beanDefinition, BeanFactory owner) {
                super(beanDefinition);
                this.owner = owner;
            }

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) {
                LookupOverride lo = (LookupOverride) getBeanDefinition().getMethodOverrides().getOverride(method);
                Object[] argsToUse = (args.length > 0 ? args : null);
                if(hasText(lo.getBeanName())){
                    return (argsToUse != null ? this.owner.getBean(lo.getBeanName(), argsToUse) : this.owner.getBean(lo.getBeanName()));
                }
                return (argsToUse != null ? this.owner.getBean(method.getReturnType(), argsToUse) : this.owner.getBean(method.getReturnType()));

            }
        }
        public  boolean hasText(String str) {
            return (str != null && !str.isEmpty() && containsText(str));
        }

        private  boolean containsText(CharSequence str) {
            int strLen = str.length();
            for (int i = 0; i < strLen; i++) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return true;
                }
            }
            return false;
        }

        private class ReplaceOverrideMethodInterceptor extends CglibIdentitySupport implements MethodInterceptor {

            private final BeanFactory owner;

            public ReplaceOverrideMethodInterceptor(RootBeanDefinition beanDefinition, BeanFactory owner) {
                super(beanDefinition);
                this.owner = owner;
            }

            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy mp) {
                ReplaceOverride ro = (ReplaceOverride) getBeanDefinition().getMethodOverrides().getOverride(method);
                MethodReplacer mr = this.owner.getBean(ro.getMethodReplacerBeanName(), MethodReplacer.class);
                return mr.reimplement(obj, method, args);
            }
        }

        private class CglibIdentitySupport {

            private final RootBeanDefinition beanDefinition;

            public CglibIdentitySupport(RootBeanDefinition beanDefinition) {
                this.beanDefinition = beanDefinition;
            }

            public RootBeanDefinition getBeanDefinition() {
                return this.beanDefinition;
            }
        }
    }
}
