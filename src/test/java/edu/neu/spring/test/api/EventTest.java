package edu.neu.spring.test.api;

import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import edu.neu.spring.event.CustomEvent;
import org.junit.Test;

public class EventTest {

    @Test
    public void testEvent() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1019129009086763L, "成功了！"));
        applicationContext.registerShutdownHook();
    }
}
