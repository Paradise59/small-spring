package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.BeansException;
import com.small.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.InvocationTargetException;

/**
 * 作用：实现Bean的创建逻辑，具备自动装配能力
 * 实现了 createBean 方法，负责Bean的实例化过程
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {
        @Override
        protected Object createBean( String beanName,  BeanDefinition beanDefinition) {
                Object bean = null;
                try {
                        bean = beanDefinition.getBeanClass().getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        throw new BeansException("Instantiation of bean failed", e);
                }
                addSingleton(beanName, bean);
                return bean;
        }
}
