package com.spring.context.support;

import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.context.ApplicationContext;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    public AbstractRefreshableApplicationContext() {
    }

    public AbstractRefreshableApplicationContext(ClassLoader classLoader) {
        super(classLoader);
    }

    public AbstractRefreshableApplicationContext(ApplicationContext parent){
        super(parent);
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return null;
    }

    @Override
    protected void refreshBeanFactory() {
        //查看是否已经创建过beanFactory，有创建就销毁
        //创建BeanFactory
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        //设置beanFactory属性参数
        //设置自定义BeanFactory
        customizeBeanFactory(beanFactory);
        //加载beanDefinitions
        loadBeanDefinitions(beanFactory);
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    private void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {

    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
