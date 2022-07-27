package edu.neu.spring.beans;

import edu.neu.spring.beans.factory.DisposableBean;
import edu.neu.spring.beans.factory.InitializingBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestService implements InitializingBean, DisposableBean {

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

}
