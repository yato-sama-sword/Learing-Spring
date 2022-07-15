package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.BeansException;

/**
 * 修改实例化后的bean的修改扩展点
 *
 * @author yato
 */
public interface BeanPostProcessor {
    /**
     * 在bean执行初始化方法之前执行此方法
     *
     * @param bean bean实例
     * @param beanName bean名
     * @return 修改后bean
     * @throws BeansException bean相关异常
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在bean执行初始化方法之后执行此方法
     *
     * @param bean bean实例
     * @param beanName bean名
     * @return 修改后bean
     * @throws BeansException bean相关异常
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
