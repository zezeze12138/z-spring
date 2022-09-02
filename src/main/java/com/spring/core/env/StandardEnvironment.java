package com.spring.core.env;

/**
 * 标准环境实现
 */
public class StandardEnvironment extends AbstractEnvironment {
    @Override
    public void setRequiredProperties(String... requiredProperties) {

    }

    @Override
    public void validateRequiredProperties() {

    }

    @Override
    public String[] getActiveProfiles() {
        return new String[0];
    }

    @Override
    public String[] getDefaultProfiles() {
        return new String[0];
    }

    @Override
    public boolean acceptsProfiles(String... profiles) {
        return false;
    }

    @Override
    public boolean acceptsProfiles(Profiles profiles) {
        return false;
    }

    @Override
    public boolean containsProperty(String key) {
        return false;
    }

    @Override
    public String getProperty(String key) {
        return null;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        return null;
    }
}
