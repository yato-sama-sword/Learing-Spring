package edu.neu.spring;

import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import edu.neu.spring.test.service.TestService;
import edu.neu.spring.test.service.definition.TestServiceDefinition;
import org.junit.Test;

public class DefaultListableBeanFactoryTest {

    @Test
    public void testBeanFactory() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("TestService", new TestServiceDefinition());
        TestService testService = (TestService) beanFactory.getBean("TestService");
        testService.testLogger();
    }

}
