package com.spring.beans.factory.support;

import com.spring.core.env.Environment;
import com.spring.core.env.EnvironmentCapable;
import com.spring.core.env.StandardEnvironment;
import com.spring.core.io.Resource;
import com.spring.core.io.ResourceLoader;
import com.spring.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 抽象的Bean定义读取器
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader, EnvironmentCapable{

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    private Environment environment;


    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        //创建路径匹配资源模式解析器
        if(this.registry instanceof ResourceLoader){
            this.resourceLoader = (ResourceLoader) this.registry;
        }else {
            this.resourceLoader = new PathMatchingResourcePatternResolver();
        }
        //配置环境信息
        if(this.registry instanceof EnvironmentCapable){
            this.environment = ((EnvironmentCapable) this.registry).getEnvironment();
        }else {
            this.environment = new StandardEnvironment();
        }
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return this.registry;
    }

    @Override
    public int loadBeanDefintions(Resource resource) {
        return 0;
    }

    @Override
    public int loadBeanDefinitions(Resource... resources) {
        int count = 0;
        for (Resource resource : resources) {
            count += loadBeanDefinitions(resource);
        }
        return count;
    }

    @Override
    public int loadBeanDefinitions(String location) {
        ResourceLoader resourceLoader = getResourceLoader();
        if(resourceLoader == null){
            throw new RuntimeException("该路径不能加载bean定义");
        }
        return 0;
    }

    @Override
    public int loadBeanDefinitions(String... location) {
        return 0;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
