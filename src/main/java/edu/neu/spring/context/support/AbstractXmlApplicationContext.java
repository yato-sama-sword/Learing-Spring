package edu.neu.spring.context.support;

import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import edu.neu.spring.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author yato
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取配置信息地址
     * @return 配置信息地址的字符串
     */
    protected abstract String[] getConfigLocations();

}
