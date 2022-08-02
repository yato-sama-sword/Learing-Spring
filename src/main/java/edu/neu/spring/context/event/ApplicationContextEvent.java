package edu.neu.spring.context.event;

import edu.neu.spring.context.ApplicationContext;
import edu.neu.spring.context.ApplicationEvent;

/**
 * 基础类
 * @author yato
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}
