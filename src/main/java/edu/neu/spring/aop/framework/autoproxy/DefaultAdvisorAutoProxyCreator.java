package edu.neu.spring.aop.framework.autoproxy;

import edu.neu.spring.aop.*;
import edu.neu.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import edu.neu.spring.aop.framework.ProxyFactory;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValues;
import edu.neu.spring.beans.factory.BeanFactory;
import edu.neu.spring.beans.factory.BeanFactoryAware;
import edu.neu.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 * 创建动态代理，并将其融入Bean生命周期
 * 之后调用方获取到的bean对象是被切面注入的对象，可以被拦截进行增强！
 * @author yato
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    /**
     * 通过synchronized实现线程安全
     */
    private final Set<Object> earlyProxyReferences = Collections.synchronizedSet(new HashSet<>());


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    /**
     * 判断bean是否实现Advice或PointCut接口
     * @param beanClass bean的Class信息
     * @return 返回yes or no
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
       if (!earlyProxyReferences.contains(beanName)) {
           return wrapIfNecessary(bean, beanName);
       }
       return bean;
    }

    /**
     * 判断是否需要进行代理，如果要返回代理，不需要直接返回目标嘞
     * @param bean bean
     * @param beanName 暂时没用
     * @return ，目标/代理
     */
    protected Object wrapIfNecessary(Object bean, String beanName) {
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }
        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        for (AspectJExpressionPointcutAdvisor advisor : advisors) {
            ClassFilter classFilter = advisor.getPointcut().getClassFilter();
            // 过滤匹配类
            if (!classFilter.matches(bean.getClass())) {
                continue;
            }
            // 实例化属性集合
            AdvisedSupport advisedSupport = new AdvisedSupport();
            // 初始化目标对象
            TargetSource targetSource = new TargetSource(bean);
            // 填充相关属性
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true);
            // 返回代理对象
            System.out.println(beanName);
            return new ProxyFactory(advisedSupport).getProxy();
        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) {
        earlyProxyReferences.add(beanName);
        return wrapIfNecessary(bean, beanName);
    }
}
