package com.spring.beans.factory.support;

import java.lang.reflect.Method;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-13  22:09
 * @Version: 1.0
 */
public class LookupOverride extends MethodOverride{
    @Override
    public boolean matches(Method method) {
        return false;
    }
}
