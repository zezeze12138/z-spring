package com.spring.beans;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-11  22:02
 * @Version: 1.0
 */
public interface PropertyValues extends Iterable<PropertyValue> {

    PropertyValue[] getPropertyValues();

    @Override
    default Iterator<PropertyValue> iterator(){
        return Arrays.asList(getPropertyValues()).iterator();
    }

    @Override
    default Spliterator<PropertyValue> spliterator() {
        return Spliterators.spliterator(getPropertyValues(), 0);
    }

    PropertyValue getPropertyValue(String propertyName);

    boolean contains(String propertyName);

    boolean isEmpty();
}
