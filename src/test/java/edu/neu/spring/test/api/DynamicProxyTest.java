package edu.neu.spring.test.api;

import edu.neu.spring.aop.AdvisedSupport;
import edu.neu.spring.aop.TargetSource;
import edu.neu.spring.aop.aspectj.AspectJExpressionPointcut;
import edu.neu.spring.aop.framework.Cglib2AopProxy;
import edu.neu.spring.aop.framework.JdkDynamicAopProxy;
import edu.neu.spring.beans.IUserDao;
import edu.neu.spring.beans.UserDao;
import edu.neu.spring.beans.UserDaoInterceptor;
import org.junit.Before;
import org.junit.Test;



public class DynamicProxyTest {

    IUserDao userDao;
    AdvisedSupport advisedSupport;
    @Before
    public void init() {
        // 目标对象
        userDao = new UserDao();
        // 组装代理信息
        advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(new TargetSource(userDao));
        advisedSupport.setMethodInterceptor(new UserDaoInterceptor());
        // 注意这里设置的表达式切入点是接口
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(* edu.neu.spring.beans.IUserDao.*(..))"));
    }

    @Test
    public void testProxy() {
        IUserDao jdkProxy = (IUserDao) new JdkDynamicAopProxy(advisedSupport).getProxy();
        System.out.println(jdkProxy.queryUserName("0"));
        System.out.println();
        IUserDao cglibProxy = (IUserDao) new Cglib2AopProxy(advisedSupport).getProxy();
        System.out.println(cglibProxy.queryUserName("1"));
    }


}
