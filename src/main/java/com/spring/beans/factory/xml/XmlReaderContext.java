package com.spring.beans.factory.xml;

import com.spring.beans.factory.parsing.ReaderContext;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.core.io.Resource;

public class XmlReaderContext extends ReaderContext {

    private final XmlBeanDefinitionReader reader;

    public XmlReaderContext(Resource resource, XmlBeanDefinitionReader reader) {
        super(resource);
        this.reader = reader;
    }

    public XmlBeanDefinitionReader getReader() {
        return reader;
    }


}
