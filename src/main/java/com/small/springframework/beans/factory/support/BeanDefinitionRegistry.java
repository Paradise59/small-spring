package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.factory.config.BeanDefinition;

/**
 * 专门管理Bean定义
 */
public interface BeanDefinitionRegistry {
        /**
         *  向注册表中注册 BeanDefinition
         * @param beanName
         * @param beanDefinition
         */
        void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
