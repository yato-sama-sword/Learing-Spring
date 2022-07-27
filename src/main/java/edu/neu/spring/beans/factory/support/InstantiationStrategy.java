package edu.neu.spring.beans.factory.support;

import javax.annotation.Nullable;
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
     * @param args bean的属性，如果进行有参构造，该项不为空
     * @return bean
     * @throws BeansException bean的相关异常
     */
    Object instantiate(BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException;
}
