package edu.neu.spring.core.convert.converter;

/**
 * 类型转换处理接口
 * @author yato
 */
public interface Converter<S, T>  {

    /**
     * 实现类型转换
     * @param source source
     * @return 类型转换后的source
     */
    T convert(S source);

}
