package edu.neu.spring.event;

import edu.neu.spring.context.ApplicationListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class CustomEventListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        log.info("收到：" + event.getSource() + "消息;时间：" + new Date());
        log.info("消息：" + event.getId() + ":" + event.getMessage());
    }

}
