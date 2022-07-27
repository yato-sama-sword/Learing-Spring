package edu.neu.spring.context;

import edu.neu.spring.beans.BeansException;

/**
 * refresh核心方法！
 * @author yato
 */
public interface ConfigurableApplicationContext extends ApplicationContext{

    /**
     * 刷新容器
     * @throws BeansException bean生命周期相关错误
     */
    void refresh() throws BeansException;

    /**
     * 注册虚拟机Hook
     */
    void registerShutdownHook();

    /**
     * 关闭虚拟机Hook和方法实现
     */
    void close();
}
