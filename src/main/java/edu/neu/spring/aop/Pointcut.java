package edu.neu.spring.aop;

/**
 * 切入点，获取JoinPoint(要增强的方法)
 * 具体来说通过ClassFilter、MethodMatcher实现
 * @author yato
 */
public interface Pointcut {
    /**
     * 获取classFilter
     * @return classFilter
     */
    ClassFilter getClassFilter();

    /**
     * 获取MethodMatcher
     * @return methodMatcher
     */
    MethodMatcher getMethodMatcher();
}
