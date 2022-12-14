package com.spring.context.support;

import com.spring.beans.factory.BeanNameAware;
import com.spring.beans.factory.InitializingBean;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.context.ApplicationContext;
import com.spring.core.env.ConfigurableEnvironment;

import java.io.IOException;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext implements BeanNameAware, InitializingBean {

    private String[] configLocations;

    private boolean setIdCalled = false;

    public AbstractRefreshableConfigApplicationContext() {
    }

    public AbstractRefreshableConfigApplicationContext(ClassLoader classLoader) {
        super(classLoader);
    }

    public AbstractRefreshableConfigApplicationContext(ApplicationContext parent) {

    }

    protected String[] getConifgLocations(){
        return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
    }

    private String[] getDefaultConfigLocations() {
        return null;
    }

    public void setConfigLocations(String... locations){
        if(locations != null){
            this.configLocations = new String[locations.length];
            for (int i = 0; i < locations.length; i++) {
                this.configLocations[i] = resolvePath(locations[i].trim());
            }
        }else{
            this.configLocations = null;
        }
    }

    protected String resolvePath(String path){
        return getEnvironment().resolveRequiredPlaceholders(path);
    }

    @Override
    public void setBeanName(String name) {
        if(!this.setIdCalled){
            super.setId(name);
            setDisplayName("ApplicationContext '"+name+"'");
        }
    }

    @Override
    public void afterPropertiesSet(){
        if(!isActive()){
            refresh();
        }
    }


}
