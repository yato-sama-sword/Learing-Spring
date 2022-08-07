package edu.neu.spring.core.convert.converter;

/**
 * 类型转换工厂
 * @author yato
 */
public interface ConverterFactory<S, R>{

    /**
     * 获取指定类型的Converter
     * @param targetType 目标类型
     * @return 可以将资源转换成目标类型的转换器
     * @param <T> Class类型
     */
    <T extends R> Converter<S, T> getConverter(Class<T> targetType);

}
