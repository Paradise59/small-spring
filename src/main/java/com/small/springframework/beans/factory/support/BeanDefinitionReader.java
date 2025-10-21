package com.small.springframework.beans.factory.support;

import com.small.springframework.BeansException;
import com.small.springframework.core.io.Resource;
import com.small.springframework.core.io.ResourceLoader;

public interface BeanDefinitionReader {
        BeanDefinitionRegistry getBeanDefinitionRegistry();

        ResourceLoader getResourceLoader();

        void loadBeanDefinitions(String location) throws BeansException;

        void loadBeanDefinitions(String... locations) throws BeansException;

        void loadBeanDefinitions(Resource... resources) throws BeansException;

        void loadBeanDefinition(Resource resource) throws BeansException;
}
