package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * @author yato
 */
public interface FactoryBean<T> {
    /**
     * 获取对象
     * @return 返回Bean对象
     * @throws BeansException runtime异常
     */
    T getObject() throws BeansException;

    /**
     * 获取Bean对象类型
     * @return 返回Bean对象类型
     */
    Class<?> getObjectType();

    /**
     * 判断Bean是否是单例对象，如果是要存放在内存中
     * @return 返回是否单例对象
     */
    boolean isSingleton();
}
