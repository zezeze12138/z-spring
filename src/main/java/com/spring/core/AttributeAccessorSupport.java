package com.spring.core;


import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 属性访问器
 */
public abstract class AttributeAccessorSupport implements AttributeAccessor, Serializable{

    private final Map<String, Object> attributes = new LinkedHashMap<>();

    @Override
    public void setAttribute(String name, Object value) {
        //属性不存空值
        if(value != null){
            this.attributes.put(name, value);
        }else {
            removeAttribute(name);
        }
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public Object removeAttribute(String name) {
        return this.attributes.remove(name);
    }

    @Override
    public boolean hasAttribute(String name) {
        return this.attributes.containsKey(name);
    }

    @Override
    public String[] attributeNames() {
        return attributes != null ? ((Collection<String>)attributes).toArray(new String[0]) : new String[0];
    }

    protected void copyAttributesFrom(AttributeAccessor source){
        String[] attributenames = source.attributeNames();
        for (String attributename : attributenames) {
            setAttribute(attributename, source.getAttribute(attributename));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeAccessorSupport that = (AttributeAccessorSupport) o;
        return Objects.equals(attributes, that.attributes);
    }

    @Override
    public int hashCode() {

        return this.attributes.hashCode();
    }
}
