package com.spring.beans.factory.support;

import com.spring.beans.BeanWrapper;
import org.apache.commons.logging.Log;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-27  22:17
 * @Version: 1.0
 */
public class ConstructorResolver {

    private final AbstractAutowireCapableBeanFactory beanFatory;

    private final Log logger;

    public ConstructorResolver(AbstractAutowireCapableBeanFactory beanFactory) {
        this.beanFatory = beanFactory;
        this.logger = beanFactory.getLogger();
    }

    public BeanWrapper instantiateUsingFactoryMethod(String beanName, RootBeanDefinition mbd, Object[] args) {
        return null;
    }
}
