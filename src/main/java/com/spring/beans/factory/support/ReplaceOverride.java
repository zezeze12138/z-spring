package com.spring.beans.factory.support;

import java.lang.reflect.Method;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-17  02:53
 * @Version: 1.0
 */
public class ReplaceOverride extends MethodOverride {

    private final String methodReplaceBeanName;

    public ReplaceOverride(String methodName, String methodReplacerBeanName) {
        super(methodName);
        this.methodReplaceBeanName = methodReplacerBeanName;
    }

    @Override
    public boolean matches(Method method) {
        return false;
    }


    public String getMethodReplacerBeanName(){
        return this.methodReplaceBeanName;
    }
}
