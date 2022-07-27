package edu.neu.spring.beans.factory.support;

import cn.hutool.core.text.CharSequenceUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.DisposableBean;
import edu.neu.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 销毁方法适配器
 * @author yato
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final String DESTROY = "destroy";
    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }


    @Override
    public void destroy() throws BeansException {
        // 1. 实现接口DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        // 2. 配置信息 destroy-method
        // 首先保证销毁方法名不能为空
        if (CharSequenceUtil.isNotEmpty(destroyMethodName)) {
            if (Boolean.FALSE.equals(bean instanceof DisposableBean && DESTROY.equals(this.destroyMethodName))) {
                try {
                    Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
                    destroyMethod.invoke(bean);
                } catch (NoSuchMethodException e) {
                    throw new BeansException("Couldn't find a destroy method named '" +
                            destroyMethodName + "' on bean with name '" + beanName + "'", e);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new BeansException("invoking failed",e);
                }
            }
        }
    }
}
