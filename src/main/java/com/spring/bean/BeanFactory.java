package com.spring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现一个简单的bean容器
 * 实现注册bean，以及获取bean的方法
 * @author yato
 */
public class BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();

    public void registerBean(String beanName, Object bean){
        beanMap.put(beanName, bean);
    }

    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
