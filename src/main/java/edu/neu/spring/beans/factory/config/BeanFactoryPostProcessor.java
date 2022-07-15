package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;

/**
 * bean实例化前，可以自定义修改BeanDefinition的属性值
 *
 * @author yato
 */
public interface BeanFactoryPostProcessor {
    /**
     * 在所有BeanDefinition加载完成后，但在bean实例化之前，提供修改BeanDefinition属性值的机制
     *
     * @param beanFactory beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
