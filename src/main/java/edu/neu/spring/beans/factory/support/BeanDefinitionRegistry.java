package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;

/**
 * 注册BeanDefinition接口
 * @author yato
 */
public interface BeanDefinitionRegistry {
    /**
     * 向容器中注册BeanDefinition
     *
     * @param beanName bean名
     * @param beanDefinition bean元信息
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName
     * @return
     * @throws BeansException 如果找不到BeanDefintion
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 是否包含指定名称的BeanDefinition
     *
     * @param beanName bean名
     * @return true/false
     */
    boolean containsBeanDefinition(String beanName);

    /**
     * 返回定义的所有bean的名称
     *
     * @return String集合
     */
    String[] getBeanDefinitionNames();
}
