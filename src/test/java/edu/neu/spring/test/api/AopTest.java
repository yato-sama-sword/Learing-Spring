package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import edu.neu.spring.aop.AdvisedSupport;
import edu.neu.spring.aop.ClassFilter;
import edu.neu.spring.aop.MethodMatcher;
import edu.neu.spring.aop.TargetSource;
import edu.neu.spring.aop.aspectj.AspectJExpressionPointcut;
import edu.neu.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import edu.neu.spring.aop.framework.ProxyFactory;
import edu.neu.spring.aop.framework.ReflectiveMethodInvocation;
import edu.neu.spring.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import edu.neu.spring.beans.IUserDao;
import edu.neu.spring.beans.UserDao;
import edu.neu.spring.beans.UserDaoBeforeAdvice;
import edu.neu.spring.beans.UserDaoInterceptor;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Slf4j
public class AopTest {
    private AdvisedSupport advisedSupport;

    @Before
    public void init() {
        // 目标对象
        IUserDao UserDao = new UserDao();
        // 组装代理信息
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(UserDao));
        advisedSupport.setMethodInterceptor(new UserDaoInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* edu.neu.spring.beans.IUserDao.*(..))"));
    }

    @Test
    public void testProxyFactory() {
        advisedSupport.setProxyTargetClass(true); // false/true，JDK动态代理、CGlib动态代理
        IUserDao proxy = (IUserDao) new ProxyFactory(advisedSupport).getProxy();
        Assert.equals(proxy.queryUserName("0"), "a");
    }

    @Test
    public void testBeforeAdvice() {
        UserDaoBeforeAdvice beforeAdvice = new UserDaoBeforeAdvice();
        MethodBeforeAdviceInterceptor interceptor = new MethodBeforeAdviceInterceptor(beforeAdvice);
        advisedSupport.setMethodInterceptor(interceptor);

        IUserDao proxy = (IUserDao) new ProxyFactory(advisedSupport).getProxy();
        Assert.equals(proxy.queryUserName("0"), "a");
    }

    @Test
    public void testAdvisor() {
        // 目标对象
        IUserDao UserDao = new UserDao();

        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression("execution(* edu.neu.spring.beans.IUserDao.*(..))");
        advisor.setAdvice(new MethodBeforeAdviceInterceptor(new UserDaoBeforeAdvice()));

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(UserDao.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(UserDao);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(true); // false/true，JDK动态代理、CGlib动态代理

            IUserDao proxy = (IUserDao) new ProxyFactory(advisedSupport).getProxy();
            Assert.equals(proxy.queryUserName("0"), "a");        
        }
    }

    @Test
    public void testAop() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:aop.xml");
        IUserDao userDao = applicationContext.getBean("UserDao", IUserDao.class);
        Assert.equals(userDao.queryUserName("0"), "a");
    }

    @Test
    public void testProxyMethod() {
        // 目标对象(可以替换成任何的目标对象)
        Object targetObj = new UserDao();

        // AOP 代理
        IUserDao proxy = (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetObj.getClass().getInterfaces(), new InvocationHandler() {
            // 方法匹配器
            MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* edu.neu.spring.beans.IUserDao.*(..))");
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (methodMatcher.matches(method, targetObj.getClass())) {
                    // 方法拦截器
                    MethodInterceptor methodInterceptor = invocation -> {
                        long start = System.currentTimeMillis();
                        try {
                            return invocation.proceed();
                        } finally {
                            log.info("监控 - Begin By AOP");
                            log.info("方法名称：" + invocation.getMethod().getName());
                            log.info("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
                            log.info("监控 - End\r\n");
                        }
                    };
                    // 反射调用
                    return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                }
                return method.invoke(targetObj, args);
            }
        });

        String result = proxy.queryUserName("0");
        log.info("测试结果：" + result);

    }
}
