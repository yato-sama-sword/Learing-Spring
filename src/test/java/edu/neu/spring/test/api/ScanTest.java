package edu.neu.spring.test.api;

import cn.hutool.core.lang.Assert;
import edu.neu.spring.beans.IUserDao;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ScanTest {

    @Test
    public void testScan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserDao userDao = applicationContext.getBean("userDao", IUserDao.class);
        Assert.notNull(userDao);
        log.info(userDao.toString());
    }

    @Test
    public void testProperty() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserDao userDao= applicationContext.getBean("userDao", IUserDao.class);
        Assert.notNull(userDao);
        log.info(userDao.toString());
    }

}
