package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 主要处理FactoryBean类对象的注册操作
 * @author yato
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取FactoryBean
     * @param beanName beanName
     * @return factoryBean
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryBeanObjectCache.get(beanName);
        return (object != NULL_OBJECT ? object : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
        // 如果是单例要把FactoryBean放入缓存中
        if (factory.isSingleton()) {
            Object object = this.factoryBeanObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factory, beanName);
                this.factoryBeanObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factory, beanName);
        }
    }

    /**
     * 实现getObject方法
     * @param factory factory
     * @param beanName beanName
     * @return FactoryBean对象
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean<?> factory, final String beanName){
        try {
            return factory.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }
}
