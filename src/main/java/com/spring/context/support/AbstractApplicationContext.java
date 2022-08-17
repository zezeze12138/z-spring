package com.spring.context.support;

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
        //准备上下文
        //prepareRefresh();
        //告诉子类去刷新内部bean工厂
        //ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        //准备bean工厂在上下文中使用
        //prepareBeanFactory(beanFactory);
        //允许在上下文子类中对bean工厂进行后处理
        //postProcessBeanFactory(beanFactory);
        //调用在上下文中注册为bean的工厂处理器
        //invokeBeanFactoryPostProcessors(beanFactory);
        //注册拦截bean创建的bean处理器
        //registerBeanPostProcessors(beanFactory);
        //为上下文初始消息源
        //initMessageSource();
        //为上下文初始事件多播
        //initApplicationEventMulticaster();
        //初始化特定上下文子类中的其他特定bean
        //onRefresh();
        //检测监听器bean并注册它们
        //实例化所有剩余的非赖加载的单例
        //finishBeanFactoryInitialization(beanFactory);
        //发布相应事件
        //finishRefresh();
    }
}
