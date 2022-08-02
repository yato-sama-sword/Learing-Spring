package edu.neu.spring.context;

import java.util.EventObject;

/**
 * 定义事件的抽象类，所有事件都需要继承这个类
 * 后续所有事件的类都需要继承这个类
 * @author yato
 */
public abstract class ApplicationEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}
