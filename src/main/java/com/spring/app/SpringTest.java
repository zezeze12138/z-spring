package com.spring.app;

import com.spring.context.ApplicationContext;
import com.spring.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    public static void main(String[] args) {
        String resource = "spring-config.xml";
        //ApplicationContext applicationContext = new ClassPathXmlApplicationContext(resource);
        String beanName = "a";
        //applicationContext.getBean(beanName);
    }

}
