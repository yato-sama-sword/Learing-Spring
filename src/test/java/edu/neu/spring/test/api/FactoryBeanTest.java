package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import edu.neu.spring.beans.UserService;
import edu.neu.spring.context.ConfigurableApplicationContext;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class FactoryBeanTest {
    private ConfigurableApplicationContext applicationContext;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");
        applicationContext.registerShutdownHook();

    }
    /**
     * 测试一下原型有无效果
     */
    @Test
    public void testSingletonOrPrototype() {
        UserService userService1 = applicationContext.getBean("userService", UserService.class);
        UserService userService2 = applicationContext.getBean("userService", UserService.class);
        // 理论上哈希值不同，会重新生成两次对象
        Assert.notEquals(userService1, userService2);
    }

    @Test
    public void testFactoryBean() {
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserName();
        log.info(result);
    }
}
