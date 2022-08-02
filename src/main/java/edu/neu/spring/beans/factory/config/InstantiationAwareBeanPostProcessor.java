package edu.neu.spring.beans.factory.config;


import edu.neu.spring.beans.BeansException;

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

}
