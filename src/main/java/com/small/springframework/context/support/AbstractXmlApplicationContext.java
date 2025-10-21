package com.small.springframework.context.support;

import com.small.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.small.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Arrays;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

        @Override
        protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
                XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
                String[] configLocations = getConfigLocations();
                if (null != configLocations){
                        beanDefinitionReader.loadBeanDefinitions(configLocations);
                }
        }

        protected abstract String[] getConfigLocations();

}