package edu.neu.spring.beans.factory.support;

import javax.annotation.Nullable;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类定义模板方法
 * @author yato
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {


    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * 获取bean的元信息
     * @param beanName bean名
     * @return BeanDefinition
     * @throws BeansException Bean相关异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 缓存或者说实例化bean
     * @param beanName bean的名字
     * @param beanDefinition  bean的元信息
     * @param args bean构造函数的参数
     * @return 创建后的bean
     * @throws BeansException Bean相关异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object...args) {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public Object doGetBean(String beanName, @Nullable Object[] args) {
        Object bean = getSingleton(beanName);
        // 缓存中有bean，或者说bean已创建
        if (bean != null) {
            return bean;
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        return createBean(beanName, beanDefinition, args);
    }
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
