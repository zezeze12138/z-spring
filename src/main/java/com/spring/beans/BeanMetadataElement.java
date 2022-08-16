package com.spring.beans;

public interface BeanMetadataElement {

    default Object getSource(){
        return null;
    }

}
