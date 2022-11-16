package com.spring.beans.factory.support;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-13  22:09
 * @Version: 1.0
 */
public class LookupOverride extends MethodOverride{

    private final String beanName;

    private Method method;

    public LookupOverride(String methodName, String beanName) {
        super(beanName);
        this.beanName = beanName;
    }

    @Override
    public boolean matches(Method method) {
        if(this.method != null){
            return method.equals(this.method);
        }else {
            return (method.getName().equals(getMethodName()) && (!isOverloaded() || Modifier.isAbstract(method.getModifiers()) || method.getParameterCount() == 0));
        }
    }

    public String getBeanName(){
        return this.beanName;
    }
}
