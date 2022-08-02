package edu.neu.spring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * 处理对象扫描装配，可以通过配置路径来扫描到@Component注解的Bean对象
 * @author yato
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }

}
