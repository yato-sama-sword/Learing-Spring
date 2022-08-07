package edu.neu.spring.core.convert.support;

import edu.neu.spring.core.convert.converter.Converter;
import edu.neu.spring.core.convert.converter.ConverterFactory;
import edu.neu.spring.utils.NumberUtil;

import javax.annotation.Nullable;

/**
 * Converts from a String any JDK-standard Number implementation.
 * 将String转换成JDK标准数字类型
 */
public class StringToNumberConverterFactory implements ConverterFactory<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        @Nullable
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            return NumberUtil.parseNumber(source, this.targetType);
        }
    }

}
