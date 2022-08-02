package edu.neu.spring.aop;

/**
 * 定义类匹配类，用于切点找到给定的接口和目标了
 * @author yato
 */
public interface ClassFilter {
    /**
     * 看目标类是否应该应用于切入点
     * @param clazz 候选目标类
     * @return 返回是否切入
     */
    boolean matches(Class<?> clazz);
}
