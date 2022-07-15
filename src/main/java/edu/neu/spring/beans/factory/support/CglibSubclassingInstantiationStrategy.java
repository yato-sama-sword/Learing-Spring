package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理实例化类，生成子类实现
 * @author yato
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        // enhancer是字节码增强器，感觉和Proxy类挺像的
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        // 通过MethodInterceptor调用
        MethodInterceptor methodInterceptor = (o, method, objects, methodProxy) -> {
            methodProxy.invokeSuper(o, objects);
            return o;
        };
        enhancer.setCallback(methodInterceptor);
        return enhancer.create();
    }
}
