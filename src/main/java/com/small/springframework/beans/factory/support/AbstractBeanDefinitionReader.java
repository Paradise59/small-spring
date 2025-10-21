package com.small.springframework.beans.factory.support;

import com.small.springframework.core.io.DefaultResourceLoader;
import com.small.springframework.core.io.ResourceLoader;

public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {
        private final BeanDefinitionRegistry beanDefinitionRegistry;

        private ResourceLoader resourceLoader;

        protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
                this(registry, new DefaultResourceLoader());
        }
        public AbstractBeanDefinitionReader(final BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
                this.beanDefinitionRegistry = beanDefinitionRegistry;
                this.resourceLoader = resourceLoader;
        }

        @Override
        public BeanDefinitionRegistry getBeanDefinitionRegistry() {
                return beanDefinitionRegistry;
        }

        @Override
        public ResourceLoader getResourceLoader() {
                return resourceLoader;
        }
}
