package edu.neu.spring.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配，找到表达式范围内匹配下目标类和方法
 * @author yato
 */
public interface MethodMatcher {
    /**
     * 判断方法是否在表达式范围内
     * @param method 待匹配的方法
     * @param targetClass 目标类
     * @return 方法是否匹配
     */
    boolean matches(Method method, Class<?> targetClass);
}
