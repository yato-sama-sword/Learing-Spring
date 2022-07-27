package edu.neu.spring.test;

import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.Pet;
import edu.neu.spring.beans.User;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.support.CglibSubclassingInstantiationStrategy;

import edu.neu.spring.beans.factory.support.SimpleInstantiationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;


@Slf4j
public class InstantiationTest {

    private final BeanDefinition userDefinition = new BeanDefinition(User.class);
    private final CglibSubclassingInstantiationStrategy cglibSubclassingInstantiationStrategy = new CglibSubclassingInstantiationStrategy();
    private final SimpleInstantiationStrategy simpleInstantiationStrategy = new SimpleInstantiationStrategy();

    private User user1;
    private User user2;

    @Before
    public void init() {
        int age = 18;
        String name = "zy";
        Pet pet = new Pet();
        Object[] args = new Object[3];
        args[0] = age;
        args[1] = name;
        args[2] = pet;
        user1 = (User) cglibSubclassingInstantiationStrategy.instantiate(userDefinition, null);
        user2 = (User) cglibSubclassingInstantiationStrategy.instantiate(userDefinition, args);
    }
    @Test
    public void testInstantiation() {
        log.info(JSONUtil.toJsonStr(user1));
        log.info(JSONUtil.toJsonStr(user2));

    }

}
