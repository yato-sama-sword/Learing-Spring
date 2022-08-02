package edu.neu.spring.beans;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class UserDaoInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return methodInvocation.proceed();
        } finally {
            log.info("监控 - Begin By AOP");
            log.info("方法名称：" + methodInvocation.getMethod());
            log.info("方法耗时：" + (System.currentTimeMillis() - start) + "ms");
            log.info("监控 - End");
        }    }
}
