package com.spring.cglib.core;


import com.spring.cglib.core.internal.LoadingCache;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Author: zengqz
 * @Description: TODO
 * @CreateTime: 2022-11-18  22:40
 * @Version: 1.0
 */
public abstract class AbstractClassGenerator implements ClassGenerator {

    private Class contextClass;

    private GeneratorStrategy strategy =  DefaultGeneratorStrategy.INSTANCE;

    private NamingPolicy namingPolicy = DefaultNamingPolicy.INSTANCE;

    private Object key;

    public void setContextClass(Class contextClass){
        this.contextClass = contextClass;
    }

    public void setStrategy(GeneratorStrategy strategy){
        if(strategy == null) {
            strategy = DefaultGeneratorStrategy.INSTANCE;
            this.strategy = strategy;
        }
    }

    public void setNamingPolicy(NamingPolicy namingPolicy) {
        if (namingPolicy == null)
            namingPolicy = DefaultNamingPolicy.INSTANCE;
        this.namingPolicy = namingPolicy;
    }
    protected static class ClassLoaderData {
        private final Set<String> reservedClassNames = new HashSet<String>();

        private final WeakReference<ClassLoader> classLoader;

        private final Predicate uniqueNamePredicate = name -> reservedClassNames.contains(name);

        private final LoadingCache<AbstractClassGenerator, Object, Object> generatedClasses;

        private static final Function<AbstractClassGenerator, Object> GET_KEY = new Function<AbstractClassGenerator, Object>() {
            public Object apply(AbstractClassGenerator gen) {
                return gen.key;
            }
        };

        public ClassLoaderData(ClassLoader classLoader, WeakReference<ClassLoader> classLoader1) {
            this.classLoader = new WeakReference<ClassLoader>(classLoader);
            Function<AbstractClassGenerator, Object> load =
                    new Function<AbstractClassGenerator, Object>() {
                        public Object apply(AbstractClassGenerator gen) {
                            Class klass = gen.generate(ClassLoaderData.this);
                            return gen.wrapCachedClass(klass);
                        }
                    };
            generatedClasses = new LoadingCache<AbstractClassGenerator, Object, Object>(GET_KEY, load);
        }
    }

    private Object wrapCachedClass(Class klass) {
        return null;
    }

    private Class generate(ClassLoaderData classLoaderData) {
        return null;
    }
}
