package edu.neu.spring.beans.factory.config;


import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValues;

/**
 * 让AOP相关的代理对象等在bean对象初始化之前先初始化
 * @author yato
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     *
     * @param beanClass bean类
     * @param beanName bean名
     * @return 代理bean或原bean
     * @throws BeansException bean相关异常
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     * @param bean bean
     * @param beanName beanName
     * @return 返回是否需要执行后续方法
     * @throws BeansException Bean相关异常
     */
    boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException;


    /**
     * 在 Bean 对象实例化完成后，设置属性操作之前执行此方法
     *
     * @param pvs 属性值
     * @param bean bean
     * @param beanName bean名字
     * @return 处理后的pvs
     * @throws BeansException bean相关异常
     */
    PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException;

    /**
     * 在 Spring 中由 SmartInstantiationAwareBeanPostProcessor#getEarlyBeanReference 提供
     * @param bean bean
     * @param beanName bean名
     * @return 未完成的Bean引用
     */
    default Object getEarlyBeanReference(Object bean, String beanName) {
        return bean;
    }

}
