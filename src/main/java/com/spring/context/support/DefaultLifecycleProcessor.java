package com.spring.context.support;

import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.BeanFactoryAware;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.context.LifecycleProcessor;

/**
 * 默认的生命周期处理器实现
 */
public class DefaultLifecycleProcessor implements LifecycleProcessor, BeanFactoryAware {

    private volatile ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        if(beanFactory instanceof ConfigurableListableBeanFactory){
            this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        }else{
            throw new RuntimeException("beanFactory类型转换错误,非ConfigurableListableBeanFactory");
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClose() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
