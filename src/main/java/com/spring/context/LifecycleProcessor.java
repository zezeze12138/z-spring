package com.spring.context;

/**
 * 生命周期处理器
 */
public interface LifecycleProcessor extends Lifecycle{

    /**
     * 上下文刷新的通知
     */
    void onRefresh();

    /**
     * 上下文关闭阶段的通知
     */
    void onClose();
}
