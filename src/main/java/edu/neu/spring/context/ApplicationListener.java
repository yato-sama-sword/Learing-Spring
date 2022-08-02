package edu.neu.spring.context;

/**
 * @author yato
 */
public interface ApplicationListener<E extends ApplicationEvent> {
    /**
     * 监听事件处理
     * @param event 监听的event
     */
    void onApplicationEvent(E event);
}
