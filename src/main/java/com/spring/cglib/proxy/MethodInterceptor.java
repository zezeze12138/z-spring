package com.spring.cglib.proxy;

import java.lang.reflect.Method;

public interface MethodInterceptor extends Callback{

    Object intercept(Object var1, Method var2, Object[] var3, MethodProxy var4);

}
