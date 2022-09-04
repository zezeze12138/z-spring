package com.spring.beans.factory.config;

import com.spring.beans.BeanMetadataElement;

/**
 * Bean定义Holder
 */
public class BeanDefinitionHolder implements BeanMetadataElement{

    private final BeanDefinition beanDefinition;

    private final String beanName;

    private final String[] aliases;

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
        this(beanDefinition, beanName, null);
    }

    public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
        this.beanDefinition = beanDefinition;
        this.beanName = beanName;
        this.aliases = aliases;
    }

    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    public String getBeanName() {
        return beanName;
    }

    public String[] getAliases() {
        return aliases;
    }

    @Override
    public Object getSource() {
        return this.beanDefinition.getSource();
    }
}
