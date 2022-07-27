package edu.neu.spring.beans.factory;

import edu.neu.spring.beans.BeansException;

/**
 * 初始化方法接口
 * @author yato
 */
public interface InitializingBean {
    /**
     * 处理属性填充后调用
     * @throws BeansException Bean异常
     */
    void afterPropertiesSet() throws BeansException;
}
