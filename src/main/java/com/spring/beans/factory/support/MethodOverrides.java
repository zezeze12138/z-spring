package com.spring.beans.factory.support;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-13  22:22
 * @Version: 1.0
 */
public class MethodOverrides {

    private final Set<MethodOverride> overrides = new CopyOnWriteArraySet<>();

    public MethodOverride getOverride(Method method) {
        MethodOverride match = null;
        for (MethodOverride candidate : this.overrides) {
            if (candidate.matches(method)) {
                match = candidate;
            }
        }
        return match;
    }



}
