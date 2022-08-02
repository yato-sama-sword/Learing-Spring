package edu.neu.spring.aop.aspectj;

import edu.neu.spring.aop.Pointcut;
import edu.neu.spring.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 实现PointcutAdvisor解耦库
 * 包装切面pointcut、拦截方法advice和具体的拦截表达式
 * 主要是为了方便在xml配置中定义
 * @author yato
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切面
      */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体的拦截方法
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;

    public void setExpression(String expression){
        this.expression = expression;
    }

    @Override
    public Pointcut getPointcut() {
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }
}
