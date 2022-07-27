package edu.neu.spring.test;

import edu.neu.spring.beans.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.reflect.Constructor;

@Slf4j
public class ConstructorTest {

    @Test
    public void testConstructor() {
        Constructor<?>[] constructors = User.class.getDeclaredConstructors();
        log.info(String.valueOf(constructors.length));
    }
}
