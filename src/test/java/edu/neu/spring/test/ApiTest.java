package edu.neu.spring.test;

import edu.neu.spring.context.support.ClassPathXmlApplicationContext;
import edu.neu.spring.core.convert.converter.Converter;
import edu.neu.spring.core.convert.support.StringToNumberConverterFactory;
import edu.neu.spring.test.bean.SchoolClass;
import edu.neu.spring.test.converter.StringToIntegerConverter;
import org.junit.Test;

public class ApiTest {
    @Test
    public void test_convert() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        SchoolClass schoolClass = applicationContext.getBean("schoolClass", SchoolClass.class);
        System.out.println("测试结果：" + schoolClass);
    }

    @Test
    public void test_StringToIntegerConverter() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer num = converter.convert("1234");
        System.out.println("测试结果：" + num);
    }

    @Test
    public void test_StringToNumberConverterFactory() {
        StringToNumberConverterFactory converterFactory = new StringToNumberConverterFactory();

        Converter<String, Integer> stringToIntegerConverter = converterFactory.getConverter(Integer.class);
        System.out.println("测试结果：" + stringToIntegerConverter.convert("1234"));

        Converter<String, Long> stringToLongConverter = converterFactory.getConverter(Long.class);
        System.out.println("测试结果：" + stringToLongConverter.convert("1234"));
    }
}
