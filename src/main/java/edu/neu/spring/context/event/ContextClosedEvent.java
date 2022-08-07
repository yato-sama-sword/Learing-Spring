package edu.neu.spring.context.event;

/**
 * 监听关闭动作
 * @author yato
 */
public class ContextClosedEvent extends ApplicationContextEvent{
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
