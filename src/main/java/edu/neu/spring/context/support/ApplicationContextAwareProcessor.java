package edu.neu.spring.context.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.context.ApplicationContext;
import edu.neu.spring.context.ApplicationContextAware;

/**
 * ApplicationContext获取需要在refresh操作时，
 * 把ApplicationContext写入BeanPostProcessor
 * 由AbstractAutowireCapableBeanFactory进行调用
 * @author yato
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
