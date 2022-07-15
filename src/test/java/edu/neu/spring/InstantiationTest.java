package edu.neu.spring;

import edu.neu.spring.beans.factory.support.CglibSubclassingInstantiationStrategy;

import edu.neu.spring.beans.factory.support.SimpleInstantiationStrategy;
import edu.neu.spring.test.service.TestService;
import edu.neu.spring.test.service.definition.TestServiceDefinition;
import org.junit.Test;


public class InstantiationTest {

    private TestServiceDefinition definition = new TestServiceDefinition();

    @Test
    public void testCglib() {
        CglibSubclassingInstantiationStrategy cglibSubclassingInstantiationStrategy = new CglibSubclassingInstantiationStrategy();
        TestService bean = (TestService) cglibSubclassingInstantiationStrategy.instantiate(definition);
        bean.testLogger();
    }

    @Test
    public void testReflect() {
        SimpleInstantiationStrategy simpleInstantiationStrategy = new SimpleInstantiationStrategy();
        TestService bean = (TestService) simpleInstantiationStrategy.instantiate(definition);
        bean.testLogger();
    }
}
