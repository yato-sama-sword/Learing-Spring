package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

import java.util.Map;

/**
 * 处理List类Bean
 * @author yato
 */
public interface ListableBeanFactory extends BeanFactory{
    /**
     * 返回指定类型的所有实例
     *
     * @param type 某一类的Class对象
     * @param <T> 指定类型
     * @return 指定类型实例
     * @throws BeansException Bean相关异常
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回定义的所有bean的名称
     * @return 所有bean的名称
     */
    String[] getBeanDefinitionNames();
}
