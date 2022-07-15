package edu.neu.spring.beans;

/**
 * 属性名及对应值，相当于一种映射
 * @author yato
 */



public class PropertyValue {

    private final String name;

    private final Object value;

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }
}
