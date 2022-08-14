package com.spring.beans.factory;

/**
 * bean名称Aware接口
 */
public interface BeanNameAware extends Aware {

    /**
     * 引用该接口的bean需要实现setBeanName方法，spring构建bean的过程将调用该方法，从而完成设值操作。
     * @param name bean名称
     */
    void setBeanName(String name);

}
