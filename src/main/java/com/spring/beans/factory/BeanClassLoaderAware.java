package com.spring.beans.factory;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-29  02:13
 * @Version: 1.0
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);

}
