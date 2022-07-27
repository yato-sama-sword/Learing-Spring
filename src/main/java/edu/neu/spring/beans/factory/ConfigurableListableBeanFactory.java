package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.AutowireCapableBeanFactory;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author yato
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
    /**
     * 根据名称查找BeanDefinition
     *
     * @param beanName bean名
     * @return bean元信息
     * @throws BeansException Bean相关异常
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 提前实例化所有单例实例
     * @throws BeansException Bean相关异常
     */
    void preInstantiateSingletons() throws BeansException;

    /**
     *  注册BeanPostProcessor
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
