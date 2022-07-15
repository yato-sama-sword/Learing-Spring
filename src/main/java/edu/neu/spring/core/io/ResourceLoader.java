package edu.neu.spring.core.io;

/**
 * @author yato
 */
public interface ResourceLoader {
    /**
     * 根据资源位置确定使用的资源加载器
     * @param location 资源位置
     * @return 资源加载器
     */
    Resource getResource(String location);
}
