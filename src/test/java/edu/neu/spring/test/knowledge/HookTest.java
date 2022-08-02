package edu.neu.spring.test.knowledge;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class HookTest {
    private void selfIntroduction() {
        log.info(this.getClass().getSimpleName());
    }
    @Test
    public void testHook() {
        // 程序退出时候调用selfIntroduction
        Runtime.getRuntime().addShutdownHook(new Thread(this::selfIntroduction));
        log.info("do nothing");
    }
}
