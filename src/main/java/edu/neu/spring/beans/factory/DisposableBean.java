package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * 定义销毁方法
 * @author yato
 */
public interface DisposableBean {
    /**
     * 销毁方法
     * @throws BeansException bean异常
     */
    void destroy() throws BeansException;
}
