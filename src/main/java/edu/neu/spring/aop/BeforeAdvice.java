package edu.neu.spring.aop;

import org.aopalliance.aop.Advice;


/**
 * 切入点选择的连接点处的方法之前执行的通知
 * 决定在JointPoint执行什么前置操作
 * 此外还有AfterAdvice等，没有实现
 * @author yato
 */
public interface BeforeAdvice extends Advice {

}
