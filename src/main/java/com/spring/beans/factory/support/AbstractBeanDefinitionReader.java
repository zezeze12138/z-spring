package com.spring.beans.factory.support;

import com.spring.core.env.Environment;
import com.spring.core.env.EnvironmentCapable;
import com.spring.core.env.StandardEnvironment;
import com.spring.core.io.Resource;
import com.spring.core.io.ResourceLoader;
import com.spring.core.io.ResourcePatternResolver;
import com.spring.core.io.support.PathMatchingResourcePatternResolver;

import javax.imageio.IIOException;
import java.io.IOException;

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
        if(resourceLoader instanceof ResourcePatternResolver){
            try{
                Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);
                int count = loadBeanDefinitions(resources);
                return count;
            }catch (Exception ex){
                throw new RuntimeException("通过路径无法解析bean定义资源", ex);
            }
        }else {
            Resource resource = resourceLoader.getResource(location);
            int count = loadBeanDefinitions(resource);
            return count;
        }
    }

    @Override
    public int loadBeanDefinitions(String... locations) {
        int count = 0;
        for(String location : locations){
            count += loadBeanDefinitions(location);
        }
        return count;
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
