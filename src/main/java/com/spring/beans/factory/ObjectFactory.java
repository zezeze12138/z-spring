package com.spring.beans.factory;

/**
 * 对象工厂
 * @param <T>
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    T getObject();

}
