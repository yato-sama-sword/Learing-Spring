package edu.neu.spring.beans.factory.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.core.io.Resource;
import edu.neu.spring.core.io.ResourceLoader;

/**
 * 读取BeanDefinition的接口
 * @author yato
 */
public interface BeanDefinitionReader {

    /**
     * 获取注册的相关方法
     * @return 注册BeanDefinition接口的实现类
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载的方法
     *
     * @return 资源加载器接口的实现类
     */
    ResourceLoader getResourceLoader();

    /**
     * 通过Resource读取BeanDefinitions
     *
     * @param resource 资源抽象和访问接口实现类
     * @throws BeansException Bean相关异常
     */
    void loadBeanDefinitions(Resource resource) throws BeansException;

    /**
     * 通过location读取BeanDefinitions
     *
     * @param location 资源地址
     * @throws BeansException Bean相关异常
     */
    void loadBeanDefinitions(String location) throws BeansException;

    /**
     * 通过locations读取BeanDefinitions
     *
     * @param locations 资源地址
     * @throws BeansException Bean相关异常
     */
    void loadBeanDefinitions(String[] locations) throws BeansException;

}
