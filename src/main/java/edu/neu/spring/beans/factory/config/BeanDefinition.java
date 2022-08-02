package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.PropertyValues;

import java.util.Objects;

/**
 * 存储bean的元信息（class类型、构造参数、属性值等）
 * 每个bean都对应BeanDefinition的实例
 * 简化后只存储class类型
 * @author yato
 */

public class BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    private Class<?> beanClass;
    private PropertyValues propertyValues;
    private String initMethodName;
    private String destroyMethodName;

    /**
     * 默认Scope为Singleton
     */
    private String scope = SCOPE_SINGLETON;
    private boolean singleton = true;
    private boolean prototype = false;

    public BeanDefinition(Class<?> beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class<?> beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public void setPrototype(boolean prototype) {
        this.prototype = prototype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanDefinition that = (BeanDefinition) o;
        return singleton == that.singleton && prototype == that.prototype && Objects.equals(beanClass, that.beanClass) && Objects.equals(propertyValues, that.propertyValues) && Objects.equals(initMethodName, that.initMethodName) && Objects.equals(destroyMethodName, that.destroyMethodName) && Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanClass, propertyValues, initMethodName, destroyMethodName, scope, singleton, prototype);
    }
}
