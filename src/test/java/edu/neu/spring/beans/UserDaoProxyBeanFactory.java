package edu.neu.spring.beans;

import edu.neu.spring.beans.factory.FactoryBean;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserDaoProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws BeansException {
        // 这里只是示例，实际上实现动态代理需要对写一个代理类实现InvocationHandler
        InvocationHandler handler = new InvocationHandler() {
            // proxy可以理解为Proxy的动态实例，一开始是没有具体值的
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("toString".equals(method.getName())) return this.toString();
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("9527", "zy");
                return "代理：" + method.getName() + "：" + hashMap.get(args[0].toString());
            }
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
