package edu.neu.spring.test.common;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.PropertyValues;
import edu.neu.spring.beans.factory.ConfigurableListableBeanFactory;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author yato
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition userBeanDefinition = beanFactory.getBeanDefinition("user");
        PropertyValues propertyValues = userBeanDefinition.getPropertyValues();
        //将person的name属性改为zy
        propertyValues.addPropertyValue(new PropertyValue("name", "zy"));
    }
}
