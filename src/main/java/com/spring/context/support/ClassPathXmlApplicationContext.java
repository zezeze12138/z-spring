package com.spring.context.support;

import com.spring.context.ApplicationContext;
import com.spring.core.env.ConfigurableEnvironment;
import com.spring.core.io.Resource;

import java.io.IOException;

/**
 * 应用上下文
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private Resource[] configResources;

    /**
     * 通过资源文件路径获取应用上下文
     * @param configLoacation
     */
    public ClassPathXmlApplicationContext(String configLoacation) {
        this(new String[]{configLoacation}, true, null);
    }

    public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, ApplicationContext parent){
        super(parent);
        setConfigLocations(configLocations);
        if(refresh){
            refresh();
        }
    }

    @Override
    public void setId(String id) {

    }

    @Override
    public void setParent(ApplicationContext parent) {

    }

    @Override
    public void setEnvironment(ConfigurableEnvironment environment) {

    }

    @Override
    public ConfigurableEnvironment getEnvironment() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getApplciationName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public long getStartupDate() {
        return 0;
    }

    @Override
    public ApplicationContext getParent() {
        return null;
    }

    @Override
    public void publishEvent(Object event) {

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

    @Override
    public void close() throws IOException {

    }
}
