package com.spring.context;

/**
 * 层级消息资源
 */
public interface HierarchicalMessageSource extends MessageSource {

    /**
     * 设置父消息资源
     * @param parent
     */
    void setParentMessageSource(MessageSource parent);

    /**
     * 获取父消息资源
     * @return
     */
    MessageSource getParentMessageSource();
}
