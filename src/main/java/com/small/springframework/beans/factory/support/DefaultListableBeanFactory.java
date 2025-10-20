package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.BeansException;
import com.small.springframework.beans.factory.config.BeanDefinition;


import java.util.HashMap;
import java.util.Map;

/**
 * 作用：完整的Bean工厂实现
 * 集成了Bean定义注册表功能，可以注册和管理BeanDefinition
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

        private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();

        @Override
        protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                        throw new BeansException("No bean named '" + beanName + "' is defined");
                }
                return beanDefinition;
        }

        @Override
        public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
                beanDefinitionMap.put(beanName, beanDefinition);
        }
}
