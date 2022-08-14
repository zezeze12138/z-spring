package com.spring.context;

/**
 * 生命周期
 */
public interface Lifecycle {


    /**
     * 开始bean
     */
    void start();

    /**
     * 停止bean
     */
    void stop();

    /**
     * 当前bean是否正在运行
     * @return
     */
    boolean isRunning();
}
