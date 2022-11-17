package com.spring.beans.factory.support;

import java.lang.reflect.Method;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-17  02:53
 * @Version: 1.0
 */
public interface MethodReplacer {

    Object reimplement(Object obj, Method method, Object[] args);

}
