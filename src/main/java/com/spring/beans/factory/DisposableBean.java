package com.spring.beans.factory;

/**
 * 可销毁的bean接口
 */
public interface DisposableBean {

    /**
     * 销毁方法
     */
    void destroy() throws Exception;

}
