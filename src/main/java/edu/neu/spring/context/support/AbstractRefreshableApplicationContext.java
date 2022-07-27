package edu.neu.spring.context.support;


import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;

/**
 * 获取Bean工厂和加载资源
 * @author yato
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory tempBeanFactory = createBeanFactory();
        loadBeanDefinitions(tempBeanFactory);
        beanFactory = tempBeanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载资源配置
     * @param beanFactory beanFactory
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
