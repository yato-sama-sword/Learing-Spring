package edu.neu.spring.context.support;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;
import edu.neu.spring.beans.factory.config.BeanFactoryPostProcessor;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.context.ConfigurableApplicationContext;
import edu.neu.spring.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 应用上下文抽象类实现
 * 继承DefaultResourceLoader是为了处理配置资源的加载
 * @author yato
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {
    @Override
    public void refresh() throws BeansException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 3.Bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);
        // 4.BeanPostProcessor 需要提前与其他Bean对象实例化之前进行注册操作
        registerBeanPostProcessors(beanFactory);
        // 5.提前实例化单例对象
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 创建beanFactory，加载beanDefinition
     * @throws BeansException bean相关异常
     */
    protected abstract void refreshBeanFactory() throws BeansException;

    /**
     * 获取BeanFactory
     * @return beanFactory
     */
    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        // 获取BeanFactoryProcessors
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public Object getBean(String beanName) throws BeansException {
        return getBeanFactory().getBean(beanName);
    }

    @Override
    public Object getBean(String beanName, Object... args) throws BeansException {
        return getBeanFactory().getBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        // 在jvm增加一个关闭的钩子，当jvm关闭时，执行close方法
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }
}
