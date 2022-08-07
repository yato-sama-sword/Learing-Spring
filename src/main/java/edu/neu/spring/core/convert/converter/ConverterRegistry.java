package edu.neu.spring.core.convert.converter;

/**
 * 类型转换注册接口
 * @author yato
 */
public interface ConverterRegistry {

    /**
     * 注册指定类型类型转换器
     * @param converter converter
     */
    void addConverter(Converter<?, ?> converter);

    /**
     * 注册通用转换器
     * @param converter converter
     */
    void addConverter(GenericConverter converter);

    /**
     * 注册类型转换器工厂
     * @param converterFactory converterFactory
     */
    void addConverterFactory(ConverterFactory<?, ?> converterFactory);

}
