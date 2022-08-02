package edu.neu.spring.aop.framework.adapter;

import edu.neu.spring.aop.MethodBeforeAdvice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 实现MethodInterceptor接口，调用before方法
 * @author yato
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice advice;

    /**
     * 需要一个空构造器
     */
    public MethodBeforeAdviceInterceptor(){

    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }


    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.advice.before(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        // proceed就是执行方法
        return methodInvocation.proceed();
    }
}
