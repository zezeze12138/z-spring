package com.spring.beans.factory.support;

import com.spring.beans.factory.config.BeanDefinition;

public abstract class BeanDefinitionReaderUtils {

    public static String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry registry){
        return generateBeanName(beanDefinition, registry, false);
    }

    public static String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry, boolean isInnerBean){
        String generatedBeanName = definition.getBeanClassName();
        if(generatedBeanName == null){
            if(definition.getParentName() != null){
                generatedBeanName = definition.getParentName() + "$child";
            }else if(definition.getFactoryBeanName() != null){
                generatedBeanName = definition.getFactoryBeanName() + "$created";
            }
        }

        String id = generatedBeanName;
        if(isInnerBean){
            // TODO: 2022/9/23 这里还少了获取hashCode
            id = generatedBeanName + "#" ;
        }else {
            return uniqueBeanName(generatedBeanName, registry);
        }

        return id;
    }

    private static String uniqueBeanName(String beanName, BeanDefinitionRegistry registry) {
        String id = beanName;
        int counter = -1;
        while(counter == -1 || registry.containBeanDefintion(id)){
            counter++;
            id = beanName + "#" + counter;
        }
        return id;
    }
}
