package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.PropertyValues;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanReference;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import edu.neu.spring.beans.Pet;
import edu.neu.spring.beans.User;
import org.junit.Test;

public class PopulatePropertyValueTest {

    @Test
    public void testPopulatePropertyValue() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        PropertyValues petValues = new PropertyValues();
        petValues.addPropertyValue(new PropertyValue("name", "WangWang"));
        BeanDefinition petDefinition = new BeanDefinition(Pet.class, petValues);
        beanFactory.registerBeanDefinition("pet", petDefinition);

        PropertyValues userValues = new PropertyValues();
        userValues.addPropertyValue(new PropertyValue("name", "zyu"));
        userValues.addPropertyValue(new PropertyValue("age", 18));
        // User注册Pet实例
        userValues.addPropertyValue(new PropertyValue("pet", new BeanReference("pet")));
        BeanDefinition userDefinition = new BeanDefinition(User.class, userValues);
        beanFactory.registerBeanDefinition("user", userDefinition);

        User user = (User) beanFactory.getBean("user");
        Assert.equals(user.getAge(), 18);
        Assert.equals(user.getName(), "zyu");
        Assert.equals(user.getPet(), beanFactory.getBean("pet"));
    }
}
