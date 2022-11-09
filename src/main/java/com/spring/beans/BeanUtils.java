package com.spring.beans;

import java.lang.reflect.Constructor; /**
 * @Author: zengqz
 * @Description: Bean工具
 * @CreateTime: 2022-11-06  17:46
 * @Version: 1.0
 */
public class BeanUtils {


    public static Object instantiateClass(Constructor<?> constructorToUse, Object... args) {

        return null;
    }

    public static Object instantiateClass(Class<?> clazz) {
        if(clazz.isInterface()){
            throw new RuntimeException("不能是接口类");
        }
        try{
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException("未发现默认的构造方法");
        }
    }
}
