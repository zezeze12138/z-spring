package com.spring.beans.factory.xml;

import com.spring.beans.factory.support.AbstractBeanDefinitionReader;
import com.spring.beans.factory.support.BeanDefinitionRegistry;
import com.spring.beans.factory.support.DefaultListableBeanFactory;
import com.spring.core.env.Environment;
import com.spring.core.io.Resource;
import com.spring.core.io.support.EncodeResource;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;

/**
 * XmlBean定义读取器
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{


    protected XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 从xml文件中加载Bean定义
     * @param encodeResource
     * @return
     */
    public int loadBeanDefinitions(EncodeResource encodeResource){
        InputStream inputStream = null;
        int count = 0;
        try {
            inputStream = encodeResource.getResource().getInputStream();
            //获取xml资源
            InputSource inputSource = new InputSource(inputStream);
            count = doLoadBeanDefinitions(inputSource, encodeResource.getResource());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }


    private int doLoadBeanDefinitions(InputSource inputSource, Resource resource) {
        //加载xml文档
        Document doc = doLoadDocument(inputSource, resource);
        //注册Bean定义
        int count = registerBeanDefinitions(doc, resource);
        return count;
    }


    /**
     * 加载文档
     * @param inputSource
     * @param resource
     * @return
     */
    private Document doLoadDocument(InputSource inputSource, Resource resource) {
        return null;
    }

    /**
     * 注册bean定义
     * @param doc
     * @param resource
     * @return
     */
    private int registerBeanDefinitions(Document doc, Resource resource) {
        int count= 0;
        DefaultBeanDefinitionDocumentReader documentReader = new DefaultBeanDefinitionDocumentReader();
        documentReader.registerBeanDefintitions(doc, createReaderContext(resource));
        return count;
    }

    private XmlReaderContext createReaderContext(Resource resource) {
        return new XmlReaderContext(resource, this);
    }


}
