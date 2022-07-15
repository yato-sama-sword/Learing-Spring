package edu.neu.spring.beans.factory.config;

/**
 * Bean的依赖
 * @author yato
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
