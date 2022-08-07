package edu.neu.spring.context.support;

import edu.neu.spring.beans.factory.FactoryBean;
import edu.neu.spring.beans.factory.InitializingBean;
import edu.neu.spring.core.convert.ConversionService;
import edu.neu.spring.core.convert.converter.Converter;
import edu.neu.spring.core.convert.converter.ConverterFactory;
import edu.neu.spring.core.convert.converter.ConverterRegistry;
import edu.neu.spring.core.convert.converter.GenericConverter;
import edu.neu.spring.core.convert.support.DefaultConversionService;
import edu.neu.spring.core.convert.support.GenericConversionService;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * 创建类型转换工厂
 * @author yato
 */
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {
    @Nullable
    private Set<?> converters;

    @Nullable
    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() {
        return conversionService;
    }

    @Override
    public Class<?> getObjectType() {
        return conversionService.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        this.conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
