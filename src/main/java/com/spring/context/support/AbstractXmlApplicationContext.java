package com.spring.context.support;

import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.spring.core.io.Resource;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{
    public AbstractXmlApplicationContext(ClassLoader classLoader) {
        super(classLoader);
    }


    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        //创建XmlBeanDefinitionReader
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        //为创建的XmlBeanDefinition设置值
        //初始化BeanDefinitionReader
        initBeanDefinitionReader(beanDefinitionReader);
        //加载loadBeanDefinitions
        loadBeanDefinitions(beanDefinitionReader);

    }


    protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader){

    }

    private void loadBeanDefinitions(XmlBeanDefinitionReader reader) {
        Resource[] configResources = getConfigResources();
        // TODO: 2022/8/31  调用reader中的loadBeanDefinitions方法
    }

    private Resource[] getConfigResources() {
        return null;
    }
}
