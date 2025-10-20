package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.BeansException;
import com.small.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

public interface InstantiationStrategy {
        /**
         *  用于实例化Bean
         * @param beanDefinition
         * @param beanName
         * @param ctor
         * @param args
         * @return
         * @throws BeansException
         */
        Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;
}
