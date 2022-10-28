package com.spring.core;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-28  23:33
 * @Version: 1.0
 */
public class NameThreadLocal<T> extends ThreadLocal<T> {

    private final String name;


    public NameThreadLocal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
