package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 集大成者，实现BeanFactory、BeanDefinitionRegistry、SingletonBeanRegistry等等接口
 * 一般是对外提供的Factory
 * @author yato
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements
        ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException(beanName + " is not defined");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitions.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        beanDefinitions.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            // 该方法判断type是否是beanClass的父类
            if (type.isAssignableFrom(beanClass)) {
                T bean = (T) getBean(beanName);
                result.put(beanName, bean);
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitions.keySet();
        return beanNames.toArray(new String[beanNames.size()]);
    }


    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public void preInstantiateSingletons() throws BeansException {
        beanDefinitions.keySet().forEach(this::getBean);
    }

}
