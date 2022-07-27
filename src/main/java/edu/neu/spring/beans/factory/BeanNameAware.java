package edu.neu.spring.beans.factory;

/**
 * 感知所属BeanName
 * @author yato
 */
public interface BeanNameAware extends Aware{
    /**
     * 感知所属bean
     * @param beanName bean名字
     */
    void setBeanName(String beanName);
}
