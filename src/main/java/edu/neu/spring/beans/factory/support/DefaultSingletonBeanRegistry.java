package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.DisposableBean;
import edu.neu.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实现管理单例bean的方法
 * @author yato
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new HashMap<>();

    public Map<String, DisposableBean> getDisposableBeans() {
        return disposableBeans;
    }

    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 注入单例bean至IoC容器
     * @param beanName bean名
     * @param singletonObject bean实例
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeans.keySet();
        String[] disposableBeanNames = keySet.toArray(new String[0]);
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            String beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
