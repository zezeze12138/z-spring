package com.spring.core.env;

/**
 * 环境信息接口
 */
public interface Environment extends PropertyResolver{

    String[] getActiveProfiles();

    String[] getDefaultProfiles();

    boolean acceptsProfiles(String... profiles);

    boolean acceptsProfiles(Profiles profiles);

}
