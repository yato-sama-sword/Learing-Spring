package edu.neu.spring.test.service.definition;

import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.test.service.TestService;

/**
 * @author yato
 */
public class TestServiceDefinition extends BeanDefinition {
    public TestServiceDefinition() {
        super(TestService.class, null);
    }
}
