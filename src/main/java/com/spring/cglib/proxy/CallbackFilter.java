package com.spring.cglib.proxy;

import java.lang.reflect.Method;

public interface CallbackFilter {

    int accpet(Method method);

    boolean equals(Object object);

}
