package edu.neu.spring.test;

import edu.neu.spring.beans.TestService;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class InitDisposeTest {
    @Test
    public void testInitDispose() {
        // 1.初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:InitDispose.xml");
        applicationContext.registerShutdownHook();
        // 2.获取Bean对象调用方法
        TestService testService = applicationContext.getBean("testService", TestService.class);
        log.info(testService.queryTestName());
    }
}
