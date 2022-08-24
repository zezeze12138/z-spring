package com.spring.context.support;

public class ApplicationListenerDetector {

    private final transient AbstractApplicationContext applicationContext;

    public ApplicationListenerDetector(AbstractApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
