package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.Pet;
import edu.neu.spring.beans.User;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.PropertyValues;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DefaultListableBeanFactoryTest {

    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues userValues = new PropertyValues();
        userValues.addPropertyValue(new PropertyValue("pet", new Pet("crazy")));
        beanFactory.registerBeanDefinition("User", new BeanDefinition(User.class, userValues));
        User user = (User) beanFactory.getBean("User", 18, "zy", new Pet());
        // 年龄和名字是构造方式创建的，pet是属性填充的
        log.info(JSONUtil.toJsonStr(user));
        User userCopy = (User) beanFactory.getBean("User");
        Assert.equals(user, userCopy);
    }
}
