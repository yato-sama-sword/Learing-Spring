package edu.neu.spring.test.api;

import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.TestService;
import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class AwareTest {

    @Test
    public void testAware() {
        // 初始化BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:aware.xml");
        applicationContext.registerShutdownHook();
        // 获取TestService
        TestService testService = applicationContext.getBean("testService", TestService.class);
        log.info(JSONUtil.toJsonStr(testService));
        log.info(JSONUtil.toJsonStr(testService.getApplicationContext()));
        log.info(JSONUtil.toJsonStr(testService.getBeanFactory()));
    }
}
