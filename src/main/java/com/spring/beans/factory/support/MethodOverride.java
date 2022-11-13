package com.spring.beans.factory.support;

import java.lang.reflect.Method;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-13  22:25
 * @Version: 1.0
 */
public abstract class MethodOverride {

    public abstract boolean matches(Method method);

}
