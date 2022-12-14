package com.spring.beans.factory.support;

import com.spring.beans.BeanMetadataAttributeAccessor;
import com.spring.beans.BeanWrapper;
import com.spring.beans.MutablePropertyValues;
import com.spring.beans.factory.config.BeanDefinition;
import com.spring.beans.factory.config.ConstructorArgumentValues;
import com.spring.core.io.Resource;
import com.spring.util.ClassUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * 抽象的Bean定义
 */
public abstract class AbstractBeanDefinition extends BeanMetadataAttributeAccessor implements BeanDefinition, Cloneable{

    private String scope = "";

    private boolean abstractFlag = false;

    private volatile Object beanClass;

    private ConstructorArgumentValues constructorArgumentValues;

    private MutablePropertyValues propertyValues;

    private final Map<String, AutowireCandidateQualifier> qualifiers = new LinkedHashMap<>();

    private Resource resource;

    private boolean synthetic = false;

    private String initMehodName;

    private boolean nonPublicAccessAllowed = true;

    private boolean enforceInitMethod = true;

    private String destroyMethodName;

    private Supplier<?> instanceSupplier;

    private MethodOverrides methodOverrides = new MethodOverrides();

    public boolean isSynthetic() {
        return synthetic;
    }

    public void setSynthetic(boolean synthetic) {
        this.synthetic = synthetic;
    }

    public AbstractBeanDefinition(BeanDefinition original) {
        setParentName(original.getParentName());
        setBeanClassName(original.getBeanClassName());
        setScope(original.getScope());
        setAbstract(original.isAbstract());
        setLazyInit(original.isLazyInit());
        setFactoryBeanName(original.getFactoryBeanName());
        setFactoryMethodName(original.getFactoryMethodName());
        setRole(original.getRole());
        setSource(original.getSource());
        copyAttributesFrom(original);
        if(original instanceof AbstractBeanDefinition){
            AbstractBeanDefinition originalAbd = (AbstractBeanDefinition) original;
            if(originalAbd.hasBeanClass()){
                setBeanClass(originalAbd.getBeanClass());
            }
            setDependsOn(originalAbd.getDependsOn());
            setPrimary(originalAbd.isPrimary());
            copyQualifiersFrom(originalAbd);
            setResource(originalAbd.getResource());
        }

    }

    public void setResource(Resource resource){
        this.resource = resource;
    }

    public Resource getResource(){
        return this.resource;
    }

    protected void copyQualifiersFrom(AbstractBeanDefinition original){
        this.qualifiers.putAll(original.qualifiers);
    }

    public Class<?> getBeanClass() {
        Object beanClassObject =  this.beanClass;
        if(beanClassObject == null){
            throw new RuntimeException("bean定义中没有明确的beanClass");
        }
        if(!(beanClassObject instanceof Class)){
            throw new RuntimeException("Bean类名["+beanClassObject+"]尚未解析为实际的类");
        }
        return (Class<?>) beanClassObject;
    }

    public AbstractBeanDefinition() {
        this(null, null);
    }

    public AbstractBeanDefinition(ConstructorArgumentValues cargs, MutablePropertyValues pvs) {
        this.constructorArgumentValues = cargs;
        this.propertyValues = pvs;
    }

    public void setAbstract(boolean abstractFlag) {
        this.abstractFlag = abstractFlag;
    }


    public abstract RootBeanDefinition cloneBeanDefinition();

    public void validate(){
        if(hasBeanClass()){
            prepareMethodOverrides();
        }
    }

    protected void prepareMethodOverrides() {
        if(hasMethodOverrides()){

        }
    }

    protected boolean hasMethodOverrides(){
        return false;
    }


    public boolean hasBeanClass(){
        return (this.beanClass instanceof Class);
    }

    public void setBeanClass(Class<?> beanClass){
        this.beanClass = beanClass;
    }



    @Override
    public BeanDefinition getOriginatingBeanDefinition() {
        return null;
    }

    @Override
    public MutablePropertyValues getPropertyValues() {
        return null;
    }

    @Override
    public String getInitMethodName() {
        return this.initMehodName;
    }

    public boolean isNonPublicAccessAllowed() {
        return this.nonPublicAccessAllowed;
    }

    public boolean isEnforceInitMethod() {
        return this.enforceInitMethod;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException {
        String className = getBeanClassName();
        if(className == null){
            return null;
        }
        Class<?> resolvedClass = ClassUtils.forName(className, classLoader);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    public Supplier<?> getInstanceSupplier(){
        return this.instanceSupplier;
    }

    public int getResolvedAutowireMode(){
        return 0;
    }

    public boolean hasConstructorArgumentValues() {
        return (this.constructorArgumentValues != null && !this.constructorArgumentValues.isEmpty());
    }

    public MethodOverrides getMethodOverrides(){
        return this.methodOverrides;
    }


}
