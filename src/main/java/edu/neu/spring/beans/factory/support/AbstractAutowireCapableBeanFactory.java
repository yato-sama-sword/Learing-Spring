package edu.neu.spring.beans.factory.support;


import cn.hutool.core.bean.BeanUtil;
import javax.annotation.Nullable;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.factory.*;
import edu.neu.spring.beans.factory.config.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实例化Bean类
 * @author yato
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    /**
     * 选择反射方式作为实例化策略
     */
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();


    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException {
        return doCreateBean(beanName, beanDefinition, args);
    }

    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException {
        Object bean;
        try {
            // 判断是否返回代理 Bean 对象
            bean = resolveBeforeInstantiation(beanName, beanDefinition);
            if (null != bean) {
                return bean;
            }
            // 构造方法参数为空就直接实例化，不为空就找到对应构造器进行实例化
            bean = instantiationStrategy.instantiate(beanDefinition, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 为bean填充属性
        applyPropertyValues(beanName, bean, beanDefinition);

        // 初始化
        bean = initializeBean(beanName, bean, beanDefinition);

        // 注册实现DisposableBean接口的Bean对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        // 判断是否为单例Bean
        if (beanDefinition.isSingleton()) {
            // addSingleton是要放入缓存里
            registerSingleton(beanName, bean);
        }
        return bean;
    }

    protected Object resolveBeforeInstantiation(String beanName, BeanDefinition beanDefinition) {
        Object bean = applyBeanPostProcessorsBeforeInstantiation(beanDefinition.getBeanClass(), beanName);
        if (null != bean) {
            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        }
        return bean;
    }

    protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
                Object result = ((InstantiationAwareBeanPostProcessor) beanPostProcessor).postProcessBeforeInstantiation(beanClass, beanName);
                if (null != result) {
                    return result;
                }
            }
        }
        return null;
    }

    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 非Singleton类型Bean不销毁
        if (!beanDefinition.isSingleton()) {
            return;
        }

        if (bean instanceof DisposableBean || CharSequenceUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    /**
     * 为bean填充属性
     * @param beanName bean名
     * @param bean bean
     * @param beanDefinition bean元信息
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()) {
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                // 判断value是不是被依赖的Bean
                if (value instanceof BeanReference) {
                    // 如果是需要获取value的实例
                    value = getBean(((BeanReference) value).getBeanName());
                }
                //通过反射设置属性
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception ex) {
            throw new BeansException("Error setting property values for bean: " + beanName, ex);
        }
    }

    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 判断bean是否实现Aware接口
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                // 获取当前的BeanClassLoader
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        //1.执行BeanPostProcessor的前置处理
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        // 执行bean的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (NoSuchMethodException e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new BeansException("Could not find an init method named on bean with name '" + beanName + "'", e);
        }
        //2.执行BeanPostProcessor的后置处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 实现InitializingBean
     * 1.处理afterPropertiesSet方法
     * 2.判断init-method是否存在，反射执行调用
     *
     * @param beanName bean名
     * @param bean bean实例
     * @param beanDefinition bean辕信息
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 1.处理afterPropertiesSet方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        // 2. 配置信息 init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if (CharSequenceUtil.isNotEmpty(initMethodName)) {
            Class<?> clazz = beanDefinition.getBeanClass();
            Method initMethod = clazz.getMethod(initMethodName);
            Assert.notNull(initMethod);
            initMethod.invoke(bean);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
