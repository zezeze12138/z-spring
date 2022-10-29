package com.spring.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import static javafx.fxml.FXMLLoader.getDefaultClassLoader;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-10-23  18:11
 * @Version: 1.0
 */
public class ClassUtils {

    private static final Map<String, Class<?>> commonClassCache = new HashMap<>(64);
    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<>(32);

    public static Class<?> forName(String className, ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> clazz = resolvePrimitiveClassName(className);
        if(clazz == null){
            clazz = commonClassCache.get(className);
        }
        if(clazz != null){
            return clazz;
        }

        if(className.endsWith("[]")){
            String elemnetClassName = className.substring(0, className.length() - "[]".length());
            Class<?> elementClass = forName(elemnetClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }
        if(className.startsWith("[L") && className.endsWith(";")){
            String elemnetClassName = className.substring("[L".length(), className.length() - 1);
            Class<?> elementClass = forName(elemnetClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }
        if(className.startsWith("[")){
            String elemnetClassName = className.substring("[".length());
            Class<?> elementClass = forName(elemnetClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }

        ClassLoader clToUser = classLoader;
        if(clToUser == null){
            clToUser = getDefaultClassLoader();
        }
        try{
            return Class.forName(className, false, clToUser);
        } catch (ClassNotFoundException ex) {
            int lastDotIndex = className.lastIndexOf('.');
            if(lastDotIndex != -1){
                String innerClassName = className.substring(0, lastDotIndex) + '$' + className.substring(lastDotIndex + 1);
                try{
                    return Class.forName(innerClassName, false, clToUser);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
            throw ex;
        }
    }

    private static Class<?> resolvePrimitiveClassName(String className) {
        Class<?> result = null;
        if(className != null && className.length() <= 8){
            result = primitiveTypeNameMap.get(className);
        }
        return result;
    }

    public static ClassLoader getDefaultClassLoader() {
        return null;
    }
}
