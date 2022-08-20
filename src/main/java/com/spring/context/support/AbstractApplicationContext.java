package com.spring.context.support;

import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.context.ConfigurableApplicationContext;
import com.spring.core.env.ConfigurableEnvironment;
import com.spring.core.io.DefaultResourceLoader;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext{


    public AbstractApplicationContext(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * 核心执行方法
     */
    @Override
    public synchronized void refresh() {
        //1.准备上下文
        prepareRefresh();
        //2.告诉子类去刷新内部bean工厂
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        //3.准备bean工厂在上下文中使用
        prepareBeanFactory(beanFactory);
        //4.允许在上下文子类中对bean工厂进行后处理
        postProcessBeanFactory(beanFactory);
        //5.调用在上下文中注册为bean的工厂处理器
        invokeBeanFactoryPostProcessors(beanFactory);
        //6.注册拦截bean创建的bean处理器
        registerBeanPostProcessors(beanFactory);
        //7.为上下文初始消息源
        initMessageSource();
        //8.为上下文初始事件多播
        initApplicationEventMulticaster();
        //9.初始化特定上下文子类中的其他特定bean
        onRefresh();
        //10.检测监听器bean并注册它们
        registerListeners();
        //11.实例化所有剩余的非赖加载的单例
        finishBeanFactoryInitialization(beanFactory);
        //12.发布相应事件
        finishRefresh();
    }


    /**
     * 准备上下文
     * 创建一个Environment对象
     * systemProperties:保存Java环境变量的键值
     * systemEnvironment:保存了系统环境变量的键值
     * 自定义properSource:保存了配置文件中的键值
     */
    protected void prepareRefresh(){

    }


    /**
     * 创建BeanFactory
     * 为beanFactory中的成员变量beanDefinitionMap进行初始化，该map的作用是保存BeanDefintion对象
     * BeanDefintion作为bean的设计蓝图，规定了bean的特征：例如单例、依赖关系、初始销毁方法
     * @return
     */
    protected ConfigurableListableBeanFactory obtainFreshBeanFactory(){
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract ConfigurableListableBeanFactory getBeanFactory();
    protected abstract void refreshBeanFactory();

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){

    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {


    }

    protected void initMessageSource(){

    }

    protected void initApplicationEventMulticaster(){

    }

    protected void onRefresh() {


    }

    private void registerListeners() {


    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void finishRefresh() {

    }


}
