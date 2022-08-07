package edu.neu.spring.context.event;

/**
 * 监听刷新动作
 * @author yato
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    public ContextRefreshedEvent(Object source) {
        super(source);
    }
}
