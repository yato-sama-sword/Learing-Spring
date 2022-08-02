package edu.neu.spring.context.support;

import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;
import edu.neu.spring.beans.factory.config.BeanFactoryPostProcessor;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.context.ApplicationEvent;
import edu.neu.spring.context.ApplicationListener;
import edu.neu.spring.context.ConfigurableApplicationContext;
import edu.neu.spring.context.event.ApplicationEventMulticaster;
import edu.neu.spring.context.event.ContextClosedEvent;
import edu.neu.spring.context.event.ContextRefreshedEvent;
import edu.neu.spring.context.event.SimpleApplicationEventMulticaster;
import edu.neu.spring.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * 应用上下文抽象类实现
 * 继承DefaultResourceLoader是为了处理配置资源的加载
 * @author yato
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1.创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        // 2.获取BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        // 3.添加ApplicationContextAwareProcessor，继承ApplicationContextAware的Bean都可以感知所属的应用上下文
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
        // 4.Bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);
        // 5.BeanPostProcessor 需要提前与其他Bean对象实例化之前进行注册操作
        registerBeanPostProcessors(beanFactory);
        // 6.初始化事件发布者
        initApplicationEventMulticaster();
        // 7.注册事件监听器
        registerListeners();
        // 8.提前实例化单例对象
        beanFactory.preInstantiateSingletons();
        // 9.发布容器刷新完成事件
        finishRefresh();
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

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener<?> listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
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

        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行单例bean销毁方法
        getBeanFactory().destroySingletons();
    }

}
