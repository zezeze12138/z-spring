package com.spring.context.support;

import com.spring.context.ApplicationContext;
import com.spring.context.ApplicationContextAware;
import com.spring.context.ConfigurableApplicationContext;

public class LiveBeansView implements LiveBeanViewMBean, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    @Override
    public String getSnapshotAsJson() {
        return null;
    }

    public static void registerApplicaitonContext(ConfigurableApplicationContext applicationContext){
        String mbeanDomain = applicationContext.getEnvironment().getProperty("spring.liveBeansView.mbeanDomain");
        // TODO: 2022/8/28 下面功能还未实现
    }
}
