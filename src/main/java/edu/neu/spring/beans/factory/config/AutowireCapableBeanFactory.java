package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.BeanFactory;

/**
 * 提供自动装配能力，并且可以暴露给外部应用
 * @author yato
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 执行BeanPostProcessor的postProcessBeforeInitialization方法
     *
     * @param existingBean 实例化后的Bean
     * @param beanName bean名称
     * @return 修改后的bean
     * @throws BeansException bean相关异常
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException;

    /**
     * 执行BeanPostProcessor的postProcessAfterInitialization方法
     *
     * @param existingBean 实例化后的Bean
     * @param beanName bean名称
     * @return 修改后的bean
     * @throws BeansException bean相关异常
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException;
}
