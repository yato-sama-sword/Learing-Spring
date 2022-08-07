package edu.neu.spring.context.annotation;


import cn.hutool.core.text.CharSequenceUtil;
import edu.neu.spring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.support.BeanDefinitionRegistry;
import edu.neu.spring.stereotype.Component;

import java.util.Set;


/**
 * 具体实现扫描包处理，在获取类信息基础上，还要获取Bean作用域和类名
 * @author yato
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                // 解析 Bean 的作用域 singleton、prototype
                String beanScope = resolveBeanScope(beanDefinition);
                if (CharSequenceUtil.isNotEmpty(beanScope)) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
        // 注册处理注解的BeanPostProcessor
        registry.registerBeanDefinition("edu.neu.spring.context.annotation.internalAutowiredAnnotationProcessor",
                new BeanDefinition(AutowiredAnnotationBeanPostProcessor.class));

    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return CharSequenceUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (CharSequenceUtil.isEmpty(value)) {
            value = CharSequenceUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }

}
