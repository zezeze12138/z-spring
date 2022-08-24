package com.spring.context;

import com.spring.beans.factory.Aware;
import com.spring.core.env.Environment;

/**
 * 环境信息Aware接口
 */
public interface EnvironmentAware extends Aware {

    /**
     * 设置环境值
     * @param environment
     */
    void setEnvironment(Environment environment);

}
