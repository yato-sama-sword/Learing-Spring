package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import edu.neu.spring.beans.Pet;
import edu.neu.spring.beans.User;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class ApplicationContextTest {
    @Test
    public void testApplicationContext() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:application-context.xml");
        User user = applicationContext.getBean("user", User.class);
        Assert.equals(user, new User(18, "zy", new Pet("Wang")));
    }
}
