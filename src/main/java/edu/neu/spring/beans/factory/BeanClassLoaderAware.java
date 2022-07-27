package edu.neu.spring.beans.factory;

/**
 * 感知所属的ClassLoader
 * @author yato
 */
public interface BeanClassLoaderAware extends Aware{
    /**
     * 感知ClassLoader
     * @param classLoader 类路径加载
     */
    void setBeanClassLoader(ClassLoader classLoader);
}
