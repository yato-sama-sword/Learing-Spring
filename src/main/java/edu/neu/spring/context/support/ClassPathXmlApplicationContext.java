package edu.neu.spring.context.support;


import edu.neu.spring.beans.BeansException;

/**
 * 应用上下文实现类
 * @author yato
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从XML中加载BeanDefinition，并刷新上下文
     * @param configLocations 配置地址
     * @throws BeansException bean相关异常
     */
    public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
        this(new String[]{configLocations});
    }

    /**
     * 从XML中加载BeanDefinition，并刷新上下文
     * @param configLocations 配置地址
     * @throws BeansException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
        this.configLocations = configLocations;
        refresh();
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }

}
