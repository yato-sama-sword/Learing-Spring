package edu.neu.spring.beans;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TestDao {

    private static Map<Integer, String> hashMap = new HashMap<>();

    public void initDataMethod() {
      log.info("init-ing");
      hashMap.put(-1, "test");
      hashMap.put(0, "test0");
      hashMap.put(1, "test1");
      hashMap.put(2, "test2");
    }

    public void destroyDataMethod() {
        log.info("destroy-ing");
        hashMap.clear();
    }

    public String queryTestName(int id) {
        return hashMap.get(id);
    }
}
