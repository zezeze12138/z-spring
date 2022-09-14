package com.spring.beans;

import com.spring.core.AttributeAccessorSupport;

/**
 * Bean元数据属性访问器
 */
public class BeanMetadataAttributeAccessor extends AttributeAccessorSupport  implements BeanMetadataElement{

    private Object source;

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getSource(){
        return this.source;
    }

}
