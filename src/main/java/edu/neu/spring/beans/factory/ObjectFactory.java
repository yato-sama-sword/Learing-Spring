package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * 存放代理对象
 * @author yato
 */
public interface ObjectFactory<T> {
    /**
     * 获取创建的半成品代理对象
     * @return 返回创建未完成的代理对象
     * @throws BeansException Bean相关异常
     */
    T getObject() throws BeansException;
}
