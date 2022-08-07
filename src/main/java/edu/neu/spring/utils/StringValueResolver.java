package edu.neu.spring.utils;

/**
 * 解析字符串值
 * @author yato
 */
public interface StringValueResolver {

    /**
     * 解析字符串值
     * @param strVal 字符串变量
     * @return 解析结果
     */
    String resolveStringValue(String strVal);

}
