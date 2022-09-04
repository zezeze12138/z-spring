package com.spring.beans.factory.xml;

import com.spring.beans.factory.config.BeanDefinitionHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 默认的Bean定义文档读取器
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader{

    private XmlReaderContext readerContext;

    private BeanDefinitionParserDelegate delegate;

    @Override
    public void registerBeanDefintitions(Document doc, XmlReaderContext xmlReaderContext) {
        this.readerContext = xmlReaderContext;
        doRegisterBeanDefinitions(doc.getDocumentElement());
    }

    private void doRegisterBeanDefinitions(Element root) {
        this.delegate = createDelegate(this.readerContext, root, this.delegate);
        preProcessXml(root);
        parseBeanDefinitions(root, delegate);
        postProcessXml(root);

    }



    private BeanDefinitionParserDelegate createDelegate(XmlReaderContext readerContext, Element root, BeanDefinitionParserDelegate parentDelegate) {
        return null;
    }

    protected void preProcessXml(Element root) {

    }

    protected void postProcessXml(Element root) {

    }


    private void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {

        // TODO: 2022/9/4 解析xml的过程
        //遍历节点调用parseDefaultElement方法进行解析
    }

    private void parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate){
        if(delegate.nodeNameEquals(ele, "import")){
            importBeanDefinitionResource(ele);
        }else if(delegate.nodeNameEquals(ele,"alias")){
            processAliasRegistration(ele);
        }else if(delegate.nodeNameEquals(ele, "bean")){
            processBeanDefinition(ele, delegate);
        }else if(delegate.nodeNameEquals(ele, "beans")){
            doRegisterBeanDefinitions(ele);
        }
    }

    private void importBeanDefinitionResource(Element ele) {

    }

    private void processAliasRegistration(Element ele) {

    }

    private void processBeanDefinition(Element ele, BeanDefinitionParserDelegate delegate) {
        BeanDefinitionHolder bdHolder = delegate.parseBeanDefinitionElement(ele);
        if(bdHolder != null){
            String beanName = bdHolder.getBeanName();
            //将BeanDefinition进行注册
            this.readerContext.getReader().getRegistry().registerBeanDefinition(beanName, bdHolder.getBeanDefinition());
            // TODO: 2022/9/4 下面还有注册bean别名的操作
        }
    }

}
