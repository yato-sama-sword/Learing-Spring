package edu.neu.spring.beans.factory.support;

import javax.annotation.Nullable;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 简单反射实现
 * @author yato
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy{
    @Override
    public Object instantiate(BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException {
        Class<?> beanClass = beanDefinition.getBeanClass();
        try {
            if (args == null) {
                // 这个是默认空构造器
                return beanClass.getDeclaredConstructor().newInstance();
            } else {
                // 获取所有的构造函数
                Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
                // 这里只比较构造函数参数和方法中参数数量是否相同
                for (Constructor<?> ctor : constructors) {
                    if (ctor.getParameterTypes().length == args.length) {
                        return ctor.newInstance(args);
                    }
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeansException("instantiate failed : " + beanClass.getName(),e);
        }
        throw new BeansException("Constructor not found");
    }
}
