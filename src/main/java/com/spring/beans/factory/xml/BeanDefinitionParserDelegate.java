package com.spring.beans.factory.xml;

import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.BeanDefinitionHolder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean定义解析器委托
 */
public class BeanDefinitionParserDelegate {

    public BeanDefinitionHolder parseBeanDefinitionElement(Element ele){
        return parseBeanDefinitionElement(ele, null);
    }

    /**
     * 解析Bean定义元素
     * @param ele
     * @param o
     * @return
     */
    private BeanDefinitionHolder parseBeanDefinitionElement(Element ele, Object o) {
        String id = ele.getAttribute("id");
        String nameAttr = ele.getAttribute("name");
        //List<String> aliases = new ArrayList<>();
        BeanDefinition beanDefinition = null;
        String beanName = id;

        String[] aliasesArray = null;
        return new BeanDefinitionHolder(beanDefinition, beanName, aliasesArray);
    }


    public boolean nodeNameEquals(Node node, String desiredName){
        return desiredName.equals(node.getNodeName()) || desiredName.equals(getLocalName(node));
    }

    public String getLocalName(Node node){
        return node.getLocalName();
    }

}
