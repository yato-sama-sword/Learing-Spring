package edu.neu.spring.beans;

/**
 * 定义进行bean注入、初始化等相关操作时，可能会产生的一些异常
 * 一般都是处理的运行时异常
 * @author yato
 */

public class BeansException extends RuntimeException {
    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
