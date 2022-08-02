package edu.neu.spring.event;


import edu.neu.spring.context.ApplicationListener;
import edu.neu.spring.context.event.ContextRefreshedEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("刷新事件：" + this.getClass().getName());
    }

}
