package edu.neu.spring.aop;

import edu.neu.spring.utils.ClassUtil;

/**
 * 被代理的目标对象
 * @author yato
 */
public class TargetSource {
    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取对象的接口信息
     * @return 返回对象接口信息
     */
    public Class<?>[] getTargetClass() {
        Class<?> clazz = this.target.getClass();
        // 如果是Cglib代理类，那么需要返回父类的接口信息，因为cglib代理类直接继承原类
        // 如果是jdk代理就不用了，jdk实现的接口是原类接口，但继承Proxy类
        clazz = ClassUtil.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        return clazz.getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
