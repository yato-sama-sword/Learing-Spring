package edu.neu.spring.beans.factory.xml;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.XmlUtil;
import edu.neu.spring.beans.BeansException;
import edu.neu.spring.beans.PropertyValue;
import edu.neu.spring.beans.factory.config.BeanDefinition;
import edu.neu.spring.beans.factory.config.BeanReference;
import edu.neu.spring.beans.factory.support.AbstractBeanDefinitionReader;
import edu.neu.spring.beans.factory.support.BeanDefinitionRegistry;
import edu.neu.spring.core.io.Resource;
import edu.neu.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yato
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /**
     * property标签内值，或许可以采用枚举
     */
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String INIT_METHOD = "init-method";
    public static final String DESTROY_METHOD = "destroy-method";


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
        }
    }

    /**
     * @param inputStream inputStream
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element) || !BEAN_ELEMENT.equals(childNodes.item(i).getNodeName())) {
                continue;
            }
            //解析bean标签
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute(ID_ATTRIBUTE);
            String name = bean.getAttribute(NAME_ATTRIBUTE);
            String className = bean.getAttribute(CLASS_ATTRIBUTE);
            String initMethodName = bean.getAttribute(INIT_METHOD);
            String destroyMethodName = bean.getAttribute(DESTROY_METHOD);

            // 获取class
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeansException("Cannot find class [" + className + "]");
            }
            //id优先于name
            String beanName = CharSequenceUtil.isNotEmpty(id) ? id : name;
            if (CharSequenceUtil.isEmpty(beanName)) {
                //如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                beanName = CharSequenceUtil.lowerFirst(clazz.getSimpleName());
            }

            // 初始化bean元信息
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element) || !PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())){
                    continue;
                }
                //解析property标签
                Element property = (Element) bean.getChildNodes().item(j);
                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                String refAttribute = property.getAttribute(REF_ATTRIBUTE);
                if (CharSequenceUtil.isEmpty(nameAttribute)) {
                    throw new BeansException("The name attribute cannot be null or empty");
                }
                Object value = valueAttribute;
                // 如果property标签内有ref的值
                if (CharSequenceUtil.isNotEmpty(refAttribute)) {
                    value = new BeanReference(refAttribute);
                }
                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                //beanName不能重名
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            //注册BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
