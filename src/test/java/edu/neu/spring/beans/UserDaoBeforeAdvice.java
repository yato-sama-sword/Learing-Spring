package edu.neu.spring.beans;

import edu.neu.spring.aop.MethodBeforeAdvice;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class UserDaoBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) {
        log.info("拦截方法: " + method.getName());
    }
}
