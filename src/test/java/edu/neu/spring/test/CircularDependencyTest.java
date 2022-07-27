package edu.neu.spring.test;

import org.junit.Test;

import java.util.logging.Logger;

/**
 * 测试循环依赖
 */

public class CircularDependencyTest {

    private final Logger logger = Logger.getLogger("edu.neu.spring.ioc");
    class A {
        private B b;

        public A() {
            logger.info("A类初始化");
        }

        public void setB(B b) {
            this.b = b;
        }
    }

    class B {
        private A a;

        public B() {
            logger.info("B类初始化");
        }

        public void setA(A a) {
            this.a = a;
        }
    }

    public void init(){
        A a = new A();
        B b = new B();
        a.setB(b);
        b.setA(a);
    }

    @Test
    public void testCircularDependency(){
        init();
    }
}
