package com.spring.context.support;

import com.spring.context.ApplicationContext;
import com.spring.context.ApplicationContextAware;
import com.spring.context.ConfigurableApplicationContext;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.LinkedHashSet;
import java.util.Set;

public class LiveBeansView implements LiveBeanViewMBean, ApplicationContextAware {

    private ConfigurableApplicationContext applicationContext;

    private static final Set<ConfigurableApplicationContext> applicationContexts = new LinkedHashSet<>();

    private static String applicationName;

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
        if(mbeanDomain != null){
            synchronized (applicationContexts){
                if(applicationContexts.isEmpty()){
                    try {
                        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                        applicationName = applicationContext.getApplciationName();
                        server.registerMBean(new LiveBeansView(), new ObjectName(mbeanDomain, "application", applicationName));
                    }catch (Throwable ex){
                        throw new RuntimeException("注册LiveBeansView MBean失败");
                    }
                }
                applicationContexts.add(applicationContext);
            }
        }
    }

    public static void unregisterApplicationContext(ConfigurableApplicationContext applicationContext){
        synchronized (applicationContexts){
            if(applicationContexts.remove(applicationContext) && applicationContexts.isEmpty()){
                try{
                    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                    String mbeanDomain = applicationContext.getEnvironment().getProperty("spring.liveBeansView.mbeanDomain");
                    if(mbeanDomain != null){
                        server.unregisterMBean(new ObjectName(mbeanDomain, "application", applicationName));
                    }
                }catch (Throwable ex){
                    throw new RuntimeException("注销LiveBeansView MBean失败");
                }finally {
                    applicationName = null;
                }
            }
        }
    }


}
