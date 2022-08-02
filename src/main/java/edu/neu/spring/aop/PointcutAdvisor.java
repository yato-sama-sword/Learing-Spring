package edu.neu.spring.aop;

/**
 * advisor中的pointcut部分
 * @author yato
 */
public interface PointcutAdvisor extends Advisor{
    /**
     * 定义获取advisor中pointcut的方法
     * @return pointcut
     */
    Pointcut getPointcut();
}
