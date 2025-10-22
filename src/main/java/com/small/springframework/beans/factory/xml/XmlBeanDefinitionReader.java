package com.small.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.small.springframework.BeansException;
import com.small.springframework.beans.PropertyValue;
import com.small.springframework.beans.factory.config.BeanDefinition;
import com.small.springframework.beans.factory.config.BeanReference;
import com.small.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.small.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.small.springframework.core.io.Resource;
import com.small.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
        public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
                super(beanDefinitionRegistry);
        }

        public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
                super(beanDefinitionRegistry, resourceLoader);
        }

        @Override
        public void loadBeanDefinitions(String location) throws BeansException {
                ResourceLoader resourceLoader = getResourceLoader();
                Resource resource = resourceLoader.getResource(location);
                loadBeanDefinition(resource);
        }

        @Override
        public void loadBeanDefinitions(String... locations) throws BeansException {
                for (String location : locations) {
                        loadBeanDefinitions(location);
                }
        }

        @Override
        public void loadBeanDefinitions(Resource... resources) throws BeansException {
                for (Resource resource : resources) {
                        loadBeanDefinition(resource);
                }
        }

        @Override
        public void loadBeanDefinition(Resource resource) throws BeansException {
                try {
                        try (InputStream inputStream = resource.getInputStream()) {
                                doLoadBeanDefinitions(inputStream);
                        }
                } catch (IOException | ClassNotFoundException e) {
                        throw new BeansException("IOException parsing XML document from " + resource, e);
                }
        }


        private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
                Document document = XmlUtil.readXML(inputStream);
                Element root = document.getDocumentElement();
                NodeList childNodes = root.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                        // 判断元素
                        if( ! (childNodes.item(i) instanceof  Element))  continue;
                        // 判断对象
                        if( ! "bean".equals(childNodes.item(i).getNodeName()))  continue;
                        // 解析标签
                        Element bean = (Element) childNodes.item(i);
                        String id = bean.getAttribute("id");
                        String name = bean.getAttribute("name");
                        String className = bean.getAttribute("class");
                        String initMethod = bean.getAttribute("init-method");
                        String destroyMethodName = bean.getAttribute("destroy-method");

                        Class<?> aClass = Class.forName(className);
                        // 确定beanName
                        String beanName = StrUtil.isNotEmpty(id) ? id :name;
                        if(StrUtil.isEmpty(beanName)) {
                                beanName = StrUtil.lowerFirst(aClass.getSimpleName());
                        }
                        // 定义Bean
                        BeanDefinition beanDefinition = new BeanDefinition(aClass);
                        beanDefinition.setInitMethodName(initMethod);
                        beanDefinition.setDestroyMethodName(destroyMethodName);

                        // 读取属性并填充
                        for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                                if( ! (bean.getChildNodes().item(j) instanceof  Element))  continue;
                                if( ! "property".equals(bean.getChildNodes().item(j).getNodeName()))  continue;
                                // 解析标签：property
                                Element property = (Element) bean.getChildNodes().item(j);
                                String attrName = property.getAttribute("name");
                                String attrValue = property.getAttribute("value");
                                String attrRef = property.getAttribute("ref");
                                // 获取属性值：引入对象、值对象
                                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                                //  创建属性信息
                                beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(attrName, value));
                        }
                        if(getBeanDefinitionRegistry().containsBeanDefinition(beanName)) {
                                throw new BeansException("Duplicate beanName " + beanName + " is not allowed");
                        }
                        // 注册BeanDefinition
                        getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);

                }
        }
}
