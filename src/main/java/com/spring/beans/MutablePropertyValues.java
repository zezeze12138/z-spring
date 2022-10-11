package com.spring.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MutablePropertyValues implements PropertyValues, Serializable{

    private final List<PropertyValue> propertyValueListl;

    public MutablePropertyValues() {
        this.propertyValueListl = new ArrayList<>(0);
    }

    @Override
    public PropertyValue[] getPropertyValues() {
        return new PropertyValue[0];
    }

    @Override
    public PropertyValue getPropertyValue(String propertyName) {
        return null;
    }

    @Override
    public boolean contains(String propertyName) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
