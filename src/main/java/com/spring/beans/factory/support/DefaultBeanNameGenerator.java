package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

public class DefaultBeanNameGenerator implements BeanNameGenerator {


    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }
}
