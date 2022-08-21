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
     * 1.准备上下文
     * 创建一个Environment对象
     * systemProperties:保存Java环境变量的键值
     * systemEnvironment:保存了系统环境变量的键值
     * 自定义properSource:保存了配置文件中的键值
     */
    protected void prepareRefresh(){

    }


    /**
     * 2.创建BeanFactory
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

    /**
     * 3.准备BeanFactory
     * 这一步完善BeanFactory，为其他各成员变量赋值
     *
     * @param beanFactory
     */
    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        //设置bean的类加载器
        //设置支持表达式解析器
        //添加beanProcessor：ApplicationContextAwareProcessor和ApplicationListenerDetector
        //添加了不可被依赖的bean,例如Aware: EnvireonmentAware, ApplicationContextAware（这些接口的实现类不能通过类型来自动注入，所以需要在这里添加）
        //向beanFactory中注册了spring框架的单例bean: environment, systemProperties等
        //注册默认的环境bean
    }

    /**
     * 4.空实现，留给子类进行扩展
     * @param beanFactory
     */
    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    /**
     * 5.执行BeanFactoryPostProcessor的后置处理方法
     * beanFactoryProcessor作用是充当beanFactory的扩展点，可以添加或修改BeanDefintion
     * 通过实现BeanFactoryPostProcessor接口可以自定义beanFactoryProcessor,当容器刷新执行到这个步骤会自动调用自定义的beanFactoryProcessor
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){

    }

    /**
     * 6.初始化并注册所有的BeanPostProcessor
     * 从BeanDefinitionMap中找出所有bean后置处理器，生成的BeanPostProcessor实例对象将保存到beanPostProcessor集合中
     * BeanPostProcessor作用是充当bean的扩展点，它们会工作在bean的实例化、依赖注入、初始化阶段前后
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {


    }

    /**
     * 7.初始化MessageSource组件
     * 可做国际化功能；消息绑定，消息解析
     */
    protected void initMessageSource(){

    }

    /**
     * 8.初始化事件派发器
     */
    protected void initApplicationEventMulticaster(){
        //获取BeanFactory
        //如果容器中是否有applicationEventMulticaster，如果有就获取，没有就创建
    }

    /**
     * 9.空实现，留给子类进行扩展
     */
    protected void onRefresh() {


    }

    /**
     * 10.将ApplicationListener注册到容器中
     * 从多种途径中找到事件监听器对象，并添加到applicationEventMulticaster维护的集合当中
     */
    private void registerListeners() {


    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void finishRefresh() {

    }


}
