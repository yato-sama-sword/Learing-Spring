package edu.neu.spring.context;

/**
 * 事件发布接口
 * @author yato
 */
public interface ApplicationEventPublisher {
    /**
     * 发布事件
     * @param event 事件
     */
    void publishEvent(ApplicationEvent event);
}
