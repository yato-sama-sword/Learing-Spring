package edu.neu.spring.stereotype;

import java.lang.annotation.*;

/**
 * 还有@Service、@Controller等类似的，在个人实现的简单的SpringMvc写了
 * @author yato
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}