package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * BeanFactory将利用BeanDefinition来生成Bean对象
 * 作为IOC容器的规范，是顶级接口，有多个实现类
 *
 * @author yato
 */
public interface BeanFactory {

    /**
     * Bean实例化的方法
     * @param beanName bean名
     * @return 返回bean实例
     * @throws BeansException bean相关异常
     */
    Object getBean(String beanName) throws BeansException;

    /**
     * 根据名称和类型查找bean
     *
     * @param name bean名称
     * @param requiredType bean的类型
     * @param <T> 相较于Object，接收参数时不需要进行强制转换
     * @return 返回bean实例
     * @throws BeansException bean相关异常
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
