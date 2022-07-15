package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;

/**
 * 实例化策略
 * @author yato
 */
public interface InstantiationStrategy {
    /**
     * 通过Bean元信息进行实例化
     * @param beanDefinition bean元信息
     * @return bean
     * @throws BeansException bean的相关异常
     */
    Object instantiate(BeanDefinition beanDefinition) throws BeansException;
}
