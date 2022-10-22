package com.spring.core;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-22  21:57
 * @Version: 1.0
 */
public abstract class DecoratingClassLoader extends ClassLoader{

    private final Set<String> excludedClasses = Collections.newSetFromMap(new ConcurrentHashMap<>(8));

    public void excludeClass(String className) {
        this.excludedClasses.add(className);
    }
}
