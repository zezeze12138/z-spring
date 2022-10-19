package com.spring.beans.factory.config;

/**
 * 作用域
 */
public interface Scope {

    void registerDestructionCallback(String name, Runnable callback);

}
