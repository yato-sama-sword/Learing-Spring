package edu.neu.spring.aop.framework;

/**
 * AOP代理的抽象，有cglib和jdk两种实现
 * @author yato
 */
public interface AopProxy {
    /**
     * 返回代理对象
     * @return 代理
     */
    Object getProxy();
}
