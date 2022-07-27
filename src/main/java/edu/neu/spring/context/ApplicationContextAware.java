package edu.neu.spring.context;

import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.Aware;

/**
 * 感知到ApplicationContext
 * @author yato
 */
public interface ApplicationContextAware extends Aware{
    /**
     * 感知应用上下文
     * @param applicationContext 应用上下文
     * @throws BeansException BeansException
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
