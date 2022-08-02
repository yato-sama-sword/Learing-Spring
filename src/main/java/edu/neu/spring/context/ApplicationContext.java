package edu.neu.spring.context;

import edu.neu.spring.beans.factory.HierarchicalBeanFactory;
import edu.neu.spring.beans.factory.ListableBeanFactory;
import edu.neu.spring.core.io.ResourceLoader;

/**
 * 上下文接口
 * @author yato
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader, ApplicationEventPublisher {

}
