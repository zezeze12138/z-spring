package com.spring.context.support;

import com.spring.beans.factory.BeanFactory;
import com.spring.beans.factory.config.BeanFactoryPostProcessor;
import com.spring.beans.factory.config.ConfigurableListableBeanFactory;
import com.spring.context.*;
import com.spring.context.event.ApplicationEventMulticaster;
import com.spring.context.event.SimpleApplicationEventMulticaster;
import com.spring.context.weaving.LoadTimeWeaverAware;
import com.spring.core.convert.ConversionService;
import com.spring.core.env.ConfigurableEnvironment;
import com.spring.core.env.Environment;
import com.spring.core.io.DefaultResourceLoader;
import com.spring.core.io.ResourceLoader;
import sun.plugin2.message.Message;

import java.util.*;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext{

    //静态指定的监听器
    private final Set<ApplicationListener<?>> applicationListeners = new LinkedHashSet<>();
    //在事件多播器初始化之前的事件
    private Set<ApplicationListener<?>> earlyApplicationListeners;
    //在事件多播器初始化之前发布的事件
    private Set<ApplicationEvent> earlyApplicationEvents;
    //Bean工厂的后置处理器列表
    private final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    //消息资源（国际化）
    private MessageSource messageSource;
    //应用事件多播器
    private ApplicationEventMulticaster applicationEventMulticaster;

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
        //初始化一些属性设置，子类自定义个性化的属性设置方法
        initPropertySources();
        //校验配置文件的属性是否合法
        getEnvironment().validateRequiredProperties();
        //存储预刷新应用的监听器
        if(this.earlyApplicationListeners == null){
            this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);
        }else{
            this.applicationListeners.clear();
            this.applicationListeners.addAll(this.earlyApplicationListeners);
        }
        //初始化早期事件集合
        this.earlyApplicationEvents = new LinkedHashSet<>();
    }

    /**
     * 子类可个性化定义属性设置方法
     */
    protected void initPropertySources(){

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
        beanFactory.setBeanClassLoader(getClassLoader());
        //设置支持表达式解析器
        //添加beanProcessor：ApplicationContextAwareProcessor和ApplicationListenerDetector
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        //添加了不可被依赖的bean,例如Aware: EnvireonmentAware, ApplicationContextAware（这些接口的实现类不能通过类型来自动注入，所以需要在这里添加）
        beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
        //注册可以解析的自动装配
        beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
        beanFactory.registerResolvableDependency(ResourceLoader.class, this);
        beanFactory.registerResolvableDependency(ApplicationContext.class, this);
        //向beanFactory中注册了spring框架的单例bean: environment, systemProperties等
        beanFactory.registerSingleton("environment", getEnvironment());
        beanFactory.registerSingleton("systemProperties", getEnvironment().getSystemProperties());
        beanFactory.registerSingleton("systemEnvironment", getEnvironment().getSystemEnvironment());
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
        //调用后置处理器注册器委托中的执行Bean工厂后置处理器方法
        PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());

    }

    public List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors(){
        return this.beanFactoryPostProcessors;
    }

    /**
     * 6.初始化并注册所有的BeanPostProcessor
     * 从BeanDefinitionMap中找出所有bean后置处理器，生成的BeanPostProcessor实例对象将保存到beanPostProcessor集合中
     * BeanPostProcessor作用是充当bean的扩展点，它们会工作在bean的实例化、依赖注入、初始化阶段前后
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        //调用后置处理器注册器委托中的注册Bean后置处理器方法
        PostProcessorRegistrationDelegate.registerBeanPostProcessors(beanFactory, this);

    }

    /**
     * 7.初始化MessageSource组件
     * 可做国际化功能；消息绑定，消息解析
     */
    protected void initMessageSource(){
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        if(beanFactory.containsLocalBean("messageSource")){
            this.messageSource = beanFactory.getBean("messageSource", MessageSource.class);
        }else{
            DelegatingMessageSource dms = new DelegatingMessageSource();
            this.messageSource = dms;
            beanFactory.registerSingleton("messageSource", this.messageSource);
        }
    }

    /**
     * 8.初始化事件派发器
     */
    protected void initApplicationEventMulticaster(){
        //获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        //如果容器中是否有applicationEventMulticaster，如果有就获取，没有就创建
        if(beanFactory.containsLocalBean("applicationEventMulticaster")){
            this.applicationEventMulticaster = beanFactory.getBean("applicationEventMulticaster", ApplicationEventMulticaster.class);
        }else {
            this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
            beanFactory.registerSingleton("applicationEventMulticaster", this.applicationEventMulticaster);
        }
    }

    ApplicationEventMulticaster getApplicationEventMulticaster(){
        return this.applicationEventMulticaster;
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
        //注册监听器
        for(ApplicationListener<?> listener : getApplicationListeners()){
            getApplicationEventMulticaster().addApplicationListener(listener);
        }
        //注册自定义的监听器
        String[] listenerBeanNames = getBeanFactory().getBeanNamesForType(ApplicationListener.class, true, false);
        for(String listenerBeanName :listenerBeanNames){
            getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
        }
        //早期的应用事件的广播
        Set<ApplicationEvent> earlyEventToProcess = this.earlyApplicationEvents;
        this.earlyApplicationEvents = null;
        if(earlyEventToProcess != null){
            for(ApplicationEvent earlyEvent : earlyEventToProcess){
                getApplicationEventMulticaster().multicastEvent(earlyEvent);
            }
        }
    }

    public Collection<ApplicationListener<?>> getApplicationListeners(){
        return this.applicationListeners;
    }

    /**
     * 11.初始化所有剩下的单实例bean
     * 这一步会将beanFactory的成员补充完毕，并且初始化剩下的所有非懒加载单例bean
     * @param beanFactory
     */
    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        //初始化属性填充转换服务
        if(beanFactory.containsBean("conversionService")){
            beanFactory.setConversionService(beanFactory.getBean("conversionService", ConversionService.class));
        }
        //初始化LoadTimeWeaverAware Bean，
        String[] weaverAwareNames = beanFactory.getBeanNamesForType(LoadTimeWeaverAware.class, false, false);
        for(String weaverAwareName : weaverAwareNames){
            getBeanFactory().getBean(weaverAwareName);
        }
        //停止使用临时类加载器进行类型匹配
        beanFactory.setTempClassLoader(null);
        //允许缓存所有的Bean定义元数据,不可进一步的修改配置
        beanFactory.freezeConfiguration();
        //实例化所有剩余的非延迟初始化的单例
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 12.完成BeanFactory的初始化创建工作；
     */
    protected void finishRefresh() {
        //初始化生命周期有关的后置处理器，BeanFactory创建完成后刷新相关的工作
        //初始化LifecycleProcessor组件
        //获取LifecycleProcessor组件
        //发布容器刷新完成事件
    }


}
