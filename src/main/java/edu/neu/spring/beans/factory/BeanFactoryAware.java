package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * 实现此接口，既能感知到所属的BeanFactory
 * @author yato
 */
public interface BeanFactoryAware extends Aware{
    /**
     * 感知所属的BeanFactory
     * @param beanFactory beanFaCTORY
     * @throws BeansException
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
