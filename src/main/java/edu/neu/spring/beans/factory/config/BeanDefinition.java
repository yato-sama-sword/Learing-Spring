package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.PropertyValues;

/**
 * 存储bean的元信息（class类型、构造参数、属性值等）
 * 每个bean都对应BeanDefinition的实例
 *
 * 简化后只存储class类型
 * @author yato
 */


public class BeanDefinition {

    private Class beanClass;
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this(beanClass, null);
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

}
