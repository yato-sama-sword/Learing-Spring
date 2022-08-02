package edu.neu.spring.aop.framework.autoproxy;

import edu.neu.spring.aop.*;
import edu.neu.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import edu.neu.spring.aop.framework.ProxyFactory;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.BeanFactory;
import edu.neu.spring.beans.factory.BeanFactoryAware;
import edu.neu.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;


/**
 * 创建动态代理，并将其融入Bean生命周期
 * 之后调用方获取到的bean对象是被切面注入的对象，可以被拦截进行增强！
 * @author yato
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }
        // 获取被代理的相关信息
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            if (!classFilter.matches(beanClass)) {
                continue;
            }
            // 属性包装类
            AdvisedSupport advisedSupport = new AdvisedSupport();
            // 默认目标对象为空
            TargetSource targetSource = null;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 填充相关属性
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);
            // 生成代理对象并返回
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return null;
    }

    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
    
}
