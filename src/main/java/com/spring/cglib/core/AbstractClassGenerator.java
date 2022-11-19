package com.spring.cglib.core;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-18  22:40
 * @Version: 1.0
 */
public abstract class AbstractClassGenerator {

    private Class contextClass;

    private GeneratorStrategy strategy =  DefaultGeneratorStrategy.INSTANCE;

    public void setContextClass(Class contextClass){
        this.contextClass = contextClass;
    }

    public void setStrategy(GeneratorStrategy strategy){
        if(strategy == null) {
            strategy = DefaultGeneratorStrategy.INSTANCE;
            this.strategy = strategy;
        }
    }

}
