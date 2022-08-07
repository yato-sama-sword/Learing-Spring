package edu.neu.spring.beans.factory.support;

import javax.annotation.Nullable;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.factory.FactoryBean;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanPostProcessor;
import edu.neu.spring.beans.factory.config.ConfigurableBeanFactory;
import edu.neu.spring.core.convert.ConversionService;
import edu.neu.spring.utils.ClassUtil;
import edu.neu.spring.utils.StringValueResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象类定义模板方法
 * @author yato
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {


    private final ClassLoader beanClassLoader = ClassUtil.getDefaultClassLoader();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();
    /**
     * 处理配置信息
     */
    private final List<StringValueResolver> embeddedValueResolvers = new ArrayList<>();

    private ConversionService conversionService;

    /**
     * 获取bean的元信息
     * @param beanName bean名
     * @return BeanDefinition
     * @throws BeansException Bean相关异常
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    /**
     * 缓存或者说实例化bean
     * @param beanName bean的名字
     * @param beanDefinition  bean的元信息
     * @param args bean构造函数的参数
     * @return 创建后的bean
     * @throws BeansException Bean相关异常
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, @Nullable Object[] args) throws BeansException;

    @Override
    public Object getBean(String beanName) throws BeansException {
        return doGetBean(beanName, null);
    }

    @Override
    public Object getBean(String beanName, Object...args) {
        return doGetBean(beanName, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        // 使用cast避免不安全转换
        return requiredType.cast(getBean(name));
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        //有则覆盖
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    public void addEmbeddedValueResolver(StringValueResolver valueResolver) {
        this.embeddedValueResolvers.add(valueResolver);
    }

    @Override
    public String resolveEmbeddedValue(String value) {
        String result = value;
        for (StringValueResolver resolver : this.embeddedValueResolvers) {
            result = resolver.resolveStringValue(result);
        }
        return result;
    }

    public Object doGetBean(final String beanName, @Nullable final Object[] args) {
        Object sharedInstance = getSingleton(beanName);
        // 缓存中有bean，或者说bean已创建
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject
            return getObjectForBeanInstance(sharedInstance, beanName);
        }
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition, args);
        // 调用此方法以区分获取FactoryBean和其他Bean
        return getObjectForBeanInstance(bean, beanName);
    }

    /**
     * 获取FactoryBean对象
     * @param beanInstance bean实例
     * @param beanName bean名
     * @return 获取factoryBean
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }
        Object factoryBean= getCachedObjectForFactoryBean(beanName);
        if (factoryBean == null) {
            factoryBean = getObjectFromFactoryBean((FactoryBean<?>) beanInstance, beanName);
        }
        return factoryBean;
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    @Override
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Nullable
    @Override
    public ConversionService getConversionService() {
        return conversionService;
    }
}
