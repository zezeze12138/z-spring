package com.spring.core;

/**
 * 优先级顺序接口
 * 值越大优先级越高
 */
public interface Ordered {

    int HIGHSET_PRECEDENCE = Integer.MIN_VALUE;

    int LOWEST_PRECEDENCE = Integer.MAX_VALUE;

    /**
     * 获取优先级
     * @return
     */
    int getOrder();
}
