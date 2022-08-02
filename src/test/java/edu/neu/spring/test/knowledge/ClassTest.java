package edu.neu.spring.test.knowledge;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import edu.neu.spring.beans.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
public class ClassTest {

    @Test
    public void testClass() {
        // 变量实质上就是类的Class对象
        Class<User> userClass = User.class;
        Assert.notNull(userClass);
        log.info(userClass.getName());
        log.info(userClass.getSimpleName());
        log.info(Arrays.toString(userClass.getFields()));
        log.info(Arrays.toString(userClass.getConstructors()));
        // 实例化方法，不难看出，默认填值的只有基础属性
        try {
            log.info(JSONUtil.toJsonStr(userClass.newInstance()));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
