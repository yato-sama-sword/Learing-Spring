package edu.neu.spring.test.common;

import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.test.entity.Pet;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yato
 */

@Slf4j
public class CustomerBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("Before: " + beanName);
        if ("pet".equals(beanName)) {
            ((Pet) bean).setName("Wang");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("Before: " + JSONUtil.toJsonStr(bean));
        return bean;
    }
}
