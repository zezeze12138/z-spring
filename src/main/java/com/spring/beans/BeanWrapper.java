package com.spring.beans;

public interface BeanWrapper extends ConfigurablePropertyAccessor {

    Object getWrappedInstance();

    Class<?> getWrappedClass();

}
