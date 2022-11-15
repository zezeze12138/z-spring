package com.spring.beans.factory.support;

import java.lang.reflect.Method;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-13  22:25
 * @Version: 1.0
 */
public abstract class MethodOverride {

    private final String methodName;

    private boolean overloaded = true;

    private Object source;

    public MethodOverride(String methodName) {
        this.methodName = methodName;
    }

    public abstract boolean matches(Method method);

    public String getMethodName(){
        return this.methodName;
    }

    public boolean isOverloaded(){
        return this.overloaded;
    }

}
