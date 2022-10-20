package com.spring.beans.factory.support;

import com.spring.beans.factory.DisposableBean;
import com.spring.beans.factory.config.BeanPostProcessor;

import java.io.Serializable;
import java.security.AccessControlContext;
import java.util.List; /**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-19  22:44
 * @Version: 1.0
 */
public class DisposableBeanAdapter implements DisposableBean, Runnable, Serializable{
    public DisposableBeanAdapter(Object bean, String beanName, RootBeanDefinition mbd, List<BeanPostProcessor> beanPostProcessors, AccessControlContext acc) {

    }

    public static boolean hasDestroyMethod(Object bean, RootBeanDefinition beanDefinition){
        if(bean instanceof DisposableBean || bean instanceof AutoCloseable){
            return true;
        }
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        // TODO: 2022/10/20 逻辑实现
        return false;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void run() {
        destroy();
    }

    public static boolean hasApplicableProcessors(Object bean, List<BeanPostProcessor> beanPostProcessors) {
        // TODO: 2022/10/20 逻辑实现
        return false;
    }
}
