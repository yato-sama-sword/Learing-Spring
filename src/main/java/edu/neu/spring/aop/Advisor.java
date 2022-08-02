package edu.neu.spring.aop;

import org.aopalliance.aop.Advice;

/**
 * 对Pointcut和Advice进行组合
 * @author yato
 */
public interface Advisor {
    /**
     * 返回切面的advice部分
     * 可能是拦截器、方法invoke前的功能增强等
     * @return  advice
     */
    Advice getAdvice();
}
