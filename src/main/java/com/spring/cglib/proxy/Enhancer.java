package com.spring.cglib.proxy;

import com.spring.cglib.core.AbstractClassGenerator;
import jdk.internal.org.objectweb.asm.ClassVisitor;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-18  22:36
 * @Version: 1.0
 */
public class Enhancer extends AbstractClassGenerator{

    private Class[] interfaces;

    private Callback[] callbacks;

    private Class superclass;

    public Enhancer() {
    }

    public void setSuperclass(Class superclass){
        if(superclass != null && superclass.isInterface()){
            setInterfaces(new Class[]{superclass});
            setContextClass(superclass);
        }else if(superclass != null && superclass.equals(Object.class)){
            this.superclass = null;
        }else {
            this.superclass = superclass;
            setContextClass(superclass);
        }
    }

    public void setInterfaces(Class[] interfaces){
        this.interfaces = interfaces;
    }

    public void setCallbacks(Callback[] callbacks){
        if(callbacks != null && callbacks.length == 0){
            throw new RuntimeException("数组不能为空");
        }
        this.callbacks = callbacks;
    }

    @Override
    public void genertateClass(ClassVisitor classVisitor) {

    }
}
