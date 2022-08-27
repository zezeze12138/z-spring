package com.spring.beans.factory;

/**
 * 层级bean工厂
 */
public interface HierarchicalBeanFactory extends BeanFactory{

    /**
     * 获取父Bean工厂
     * @return
     */
    BeanFactory getParentBeanFactory();

    /**
     * 查看Bean工厂中是否包含了本地Bean
     * @param name
     * @return
     */
    boolean containsLocalBean(String name);

}
