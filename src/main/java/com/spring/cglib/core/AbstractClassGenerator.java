package com.spring.cglib.core;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-18  22:40
 * @Version: 1.0
 */
public abstract class AbstractClassGenerator {

    private Class contextClass;

    public void setContextClass(Class contextClass){
        this.contextClass = contextClass;
    }

}
