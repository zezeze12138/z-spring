package com.spring.context.support;

import com.spring.beans.factory.BeanNameAware;
import com.spring.beans.factory.InitializingBean;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.context.ApplicationContext;
import com.spring.core.env.ConfigurableEnvironment;

import java.io.IOException;

public class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext implements BeanNameAware, InitializingBean {

    public AbstractRefreshableConfigApplicationContext() {
    }

    public AbstractRefreshableConfigApplicationContext(ClassLoader classLoader) {
        super(classLoader);
    }

    public AbstractRefreshableConfigApplicationContext(ApplicationContext parent) {

    }

    protected String resolvePath(String path){
        return null;
    }

    @Override
    public void setBeanName(String name) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {

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
