package edu.neu.spring.core.convert.support;


import edu.neu.spring.core.convert.converter.ConverterRegistry;

/**
 * 实现类型转换服务
 * @author yato
 */
public class DefaultConversionService extends GenericConversionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        // 添加各类类型转换工厂
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
    }

}
