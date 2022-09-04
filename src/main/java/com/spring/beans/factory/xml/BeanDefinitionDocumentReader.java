package com.spring.beans.factory.xml;

import org.w3c.dom.Document;

/**
 * Bean定义文档读取器
 */
public interface BeanDefinitionDocumentReader {

    void registerBeanDefintitions(Document doc, XmlReaderContext xmlReaderContext);

}
