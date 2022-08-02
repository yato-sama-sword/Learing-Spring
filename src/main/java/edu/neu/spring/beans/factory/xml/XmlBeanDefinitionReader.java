package edu.neu.spring.beans.factory.xml;

import cn.hutool.core.text.CharSequenceUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanReference;
import edu.neu.spring.beans.factory.support.AbstractBeanDefinitionReader;
import edu.neu.spring.beans.factory.support.BeanDefinitionRegistry;
import edu.neu.spring.context.annotation.ClassPathBeanDefinitionScanner;
import edu.neu.spring.core.io.Resource;
import edu.neu.spring.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author yato
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /**
     * property标签内值，或许可以采用枚举
     */
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";

    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD = "init-method";
    public static final String DESTROY_METHOD = "destroy-method";

    public static final String SCOPE_ATTRIBUTE = "scope";


    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 先从location获取Resource，再通过resource读取bean元信息
     *
     * @param location 资源地址
     * @throws BeansException Beans相关异常
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        // 使用try-with-resource：不必考虑资源的关闭
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        } catch (DocumentException | ClassNotFoundException e) {
            throw new BeansException("Load file failed" ,e);
        }
    }

    /**
     * @param inputStream inputStream
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        SAXReader reader = SAXReader.createDefault();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element componentScan = root.element(COMPONENT_SCAN_ELEMENT);
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (CharSequenceUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }

        List<Element> beanList = root.elements(BEAN_ELEMENT);
        for (Element bean : beanList) {

            String id = bean.attributeValue(ID_ATTRIBUTE);
            String name = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            String initMethod = bean.attributeValue(INIT_METHOD);
            String destroyMethodName = bean.attributeValue(DESTROY_METHOD);
            String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);

            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级 id > name
            String beanName = CharSequenceUtil.isNotEmpty(id) ? id : name;
            if (CharSequenceUtil.isEmpty(beanName)) {
                beanName = CharSequenceUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            if (CharSequenceUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);
            // 读取属性并填充
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue(NAME_ATTRIBUTE);
                String attrValue = property.attributeValue(VALUE_ATTRIBUTE);
                String attrRef = property.attributeValue(REF_ATTRIBUTE);
                // 获取属性值：引入对象、值对象
                Object value = CharSequenceUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册 BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    private void scanPackage(String scanPath) {
        String[] basePackages = CharSequenceUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}
