package edu.neu.spring.beans.factory.config;

import edu.neu.spring.beans.factory.HierarchicalBeanFactory;
import edu.neu.spring.core.convert.ConversionService;
import edu.neu.spring.utils.StringValueResolver;

import javax.annotation.Nullable;

/**
 * 使用统一方式对外暴露单例管理方式
 * 具体而言是提供beanDefinition的解析、注册功能，此外还负责解决单例的循环依赖问题
 * @author yato
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 注册BeanPostProcessor
     * @param beanPostProcessor beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例对象
     */
    void destroySingletons();


    /**
     * 添加字符串解析器解析注解属性
     * @param valueResolver 字符串解析器
     */
    void addEmbeddedValueResolver(StringValueResolver valueResolver);

    /**
     * 处理如注解属性一类的值
     * @param value 要处理的属性
     * @return 处理后的属性
     */
    String resolveEmbeddedValue(String value);

    /**
     * 注册转换服务
     * @param conversionService 类型转换服务
     */
    void setConversionService(ConversionService conversionService);

    /**
     * 获取类型转换服务
     * @return ConversionService
     */
    @Nullable
    ConversionService getConversionService();

}
