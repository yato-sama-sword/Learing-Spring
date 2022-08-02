package edu.neu.spring.aop;

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
     * 返回代理目标所实现的接口
     * @return 返回对象类型
     */
    public Class<?>[] getTargetClass() {
        return this.target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return this.target;
    }
}
