package edu.neu.spring.aop;

import java.lang.reflect.Method;

/**
 * 在方法执行前进行拦截
 * @author yato
 */
public interface MethodBeforeAdvice extends BeforeAdvice{
    /**
     * 在方法被invoke前进行拦截
     * @param method 执行invoke的方法
     * @param args 方法参数
     * @param target 方法调用的目标对象
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
