package edu.neu.spring.beans.factory.config;

/**
 * 保存已经初始化完成的单例bean对象的接口
 * 在Spring源码中定义获取单例对象、注册单例对象、获取单例对象名称等方法
 * 这里简化为只实现获取单例对象
 * 其直接的实现类为DefaultSingletonBeanRegistry
 * @author yato
 */
public interface SingletonBeanRegistry {

    /**
     * 可以用来检查缓存中是否有bean
     * @param beanName bean名称
     * @return 返回bean实例
     */
    Object getSingleton(String beanName);
}
