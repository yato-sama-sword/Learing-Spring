package edu.neu.spring.beans.factory.support;

import javax.annotation.Nullable;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.*;

import java.lang.reflect.Constructor;

/**
 * cglib动态代理实例化类，生成子类实现
 * @author yato
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException {
        // enhancer是字节码增强器，感觉和Proxy类挺像的
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        // NoOp和MethodInterceptor的区别记在笔记中
        enhancer.setCallback(new NoOp() {
        });
        if (args == null) {
            return enhancer.create();
        } else {
            for (Constructor<?> ctor : beanDefinition.getBeanClass().getDeclaredConstructors()) {
                if (ctor.getParameterTypes().length == args.length) {
                    return enhancer.create(ctor.getParameterTypes(), args);
                }
            }
        }
        // 如果参数不为空，又找不到构造方法
        throw new BeansException("Constructor not found");
    }
}
