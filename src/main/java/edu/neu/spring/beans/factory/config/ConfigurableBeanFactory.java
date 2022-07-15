package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.factory.HierarchicalBeanFactory;

/**
 * 使用统一方式对外暴露单例管理方式
 * 具体而言是提供beanDefinition的解析、注册功能，此外还负责解决单例的循环依赖问题
 * @author yato
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 注册BeanPostProcessor
     *
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
