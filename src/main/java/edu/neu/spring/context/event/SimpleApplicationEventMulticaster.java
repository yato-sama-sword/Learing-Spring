package edu.neu.spring.context.event;

import edu.neu.spring.beans.factory.BeanFactory;
import edu.neu.spring.context.ApplicationEvent;
import edu.neu.spring.context.ApplicationListener;

/**
 * 简单实现广播方法
 * @author yato
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        // 让大家伙都去监听该事件
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
