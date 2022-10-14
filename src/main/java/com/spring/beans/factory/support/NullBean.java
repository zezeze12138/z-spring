package com.spring.beans.factory.support;

/**
 * @Author: zengqz
 * @Description: 空Bean
 * @CreateTime: 2022-10-14  22:18
 * @Version: 1.0
 */
final class NullBean {

    public NullBean() {
    }

    @Override
    public int hashCode() {
        return NullBean.class.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj || obj == null);
    }

    @Override
    public String toString() {
        return "null";
    }
}
