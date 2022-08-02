package edu.neu.spring.beans;

import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.factory.*;
import edu.neu.spring.context.ApplicationContext;
import edu.neu.spring.context.ApplicationContextAware;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestService implements InitializingBean, DisposableBean, BeanFactoryAware, BeanClassLoaderAware, BeanNameAware, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private int id;
    private String name;
    private TestDao testDao;
    @Override
    public void destroy() throws BeansException {
        log.info("TestService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeansException {
        log.info("TestService.afterPropertiesSet");
    }

    public String queryTestName() {
        return testDao.queryTestName(id);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        log.info("ClassLoader: " + JSONUtil.toJsonStr(classLoader));
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String beanName) {
        log.info("BeanName is: " + beanName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}
