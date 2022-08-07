package edu.neu.spring.core.convert;


import javax.annotation.Nullable;

/**
 * 类型转换抽象接口
 * @author yato
 */
public interface ConversionService {

    /**
     * 判断是否能进行转换
     * @param sourceType 资源类型
     * @param targetType 目标类型
     * @return 判断结果
     */
    boolean canConvert(@Nullable Class<?> sourceType, Class<?> targetType);

    /**
     * 将source转换成特定类型
     * @param source 资源
     * @param targetType 目标类型
     * @return 返回转换后的source
     * @param <T> 指定类型
     */
    <T> T convert(Object source, Class<T> targetType);

}
