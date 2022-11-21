package com.spring.cglib.core;

import jdk.internal.org.objectweb.asm.ClassVisitor;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-21  22:29
 * @Version: 1.0
 */
public interface ClassGenerator {

    void genertateClass(ClassVisitor classVisitor);

}
