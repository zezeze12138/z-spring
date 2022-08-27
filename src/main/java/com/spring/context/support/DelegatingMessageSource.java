package com.spring.context.support;

import com.spring.context.HierarchicalMessageSource;
import com.spring.context.MessageSource;

/**
 * 委托的消息资源
 */
public class DelegatingMessageSource implements HierarchicalMessageSource {

    private MessageSource parentMessageSource;

    @Override
    public void setParentMessageSource(MessageSource parent) {
        this.parentMessageSource = parent;
    }

    @Override
    public MessageSource getParentMessageSource() {
        return this.parentMessageSource;
    }
}
