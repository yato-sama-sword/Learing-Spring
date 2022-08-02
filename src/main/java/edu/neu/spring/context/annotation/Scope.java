package edu.neu.spring.context.annotation;

import java.lang.annotation.*;

/**
 * type和method对应的是类和方法、runtime就是运行时、Documented注解就是生成文档时会显示
 * 配置作用域的自定义注解
 * @author yato
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    // 默认scope是单例
    String value() default "singleton";
}
