package com.spring.context.support;

import com.spring.beans.factory.config.BeanPostProcessor;
import com.spring.context.ConfigurableApplicationContext;

public class ApplicationContextAwareProcessor implements BeanPostProcessor{

    private final ConfigurableApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }





}
