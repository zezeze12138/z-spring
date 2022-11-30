package com.spring.cglib.core;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-19  23:31
 * @Version: 1.0
 */
public interface GeneratorStrategy {

    <T> byte[] generate(ClassGenerator classGenerator);

}
