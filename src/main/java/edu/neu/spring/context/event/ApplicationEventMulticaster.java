package edu.neu.spring.context.event;

import edu.neu.spring.context.ApplicationEvent;
import edu.neu.spring.context.ApplicationListener;

/**
 * 事件广播器
 * 定义添加、删除监听器和广播事件的方法
 * 最终推送时间消息也由该接口方法处理谁接收事件
 * @author yato
 */
public interface ApplicationEventMulticaster {
    /**
     * 添加事件监听器
     * @param listener 事待添加件监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);
    /**
     * 移除事件监听器
     * @param listener 待移除事件监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * @param event 要广播的事件
     */
    void multicastEvent(ApplicationEvent event);
}
