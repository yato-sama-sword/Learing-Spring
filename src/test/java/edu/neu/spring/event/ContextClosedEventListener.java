package edu.neu.spring.event;


import edu.neu.spring.context.ApplicationListener;
import edu.neu.spring.context.event.ContextClosedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("关闭事件：" + this.getClass().getName());
    }

}
