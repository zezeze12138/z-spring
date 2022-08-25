package com.spring.context.support;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.List;

/**
 * 后置处理器注册器委托处理
 */
final class PostProcessorRegistrationDelegate {

    /**
     * 调用Bean工厂的后置处理器
     * @param beanFactory
     * @param beanFactoryPostProcessors
     */
    public static void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory,
                                                       List<BeanFactoryPostProcessor> beanFactoryPostProcessors){

    }

    /**
     * 注册Bean的后置工厂
     * @param beanFactory
     * @param applicationContext
     */
    public static void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory,
                                                  AbstractApplicationContext applicationContext){

    }


}
