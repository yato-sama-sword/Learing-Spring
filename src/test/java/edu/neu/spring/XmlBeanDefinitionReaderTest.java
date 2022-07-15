package edu.neu.spring;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.factory.support.DefaultListableBeanFactory;
import edu.neu.spring.beans.factory.xml.XmlBeanDefinitionReader;
import edu.neu.spring.test.entity.Pet;
import edu.neu.spring.test.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class XmlBeanDefinitionReaderTest {

    @Test
    public void testXmlBeanDefinitionReader() throws Exception {

        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        User user = (User) beanFactory.getBean("user");
        Assert.equals(user.getName(), "zyu");
        Assert.equals(user.getAge(), 18);
        // 序列化看看效果
        String userString = JSONUtil.toJsonStr(user);
        log.info(userString);
        System.out.println(userString);
        Pet pet = (Pet) beanFactory.getBean("pet");
        Assert.equals(pet, user.getPet());
    }
}
