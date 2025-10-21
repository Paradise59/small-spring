package com.small.springframework.beans.factory;

import com.small.springframework.BeansException;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory{
        /**
         * 获取所有类型为T的Bean
         * @param type
         * @param <T>
         * @return
         * @throws BeansException
         */
        <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

        /**
         * Return the names of all beans defined in this registry.
         *
         * 返回注册表中所有的Bean名称
         */
        String[] getBeanDefinitionNames();

        // String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType)：获取所有带某个注解的Bean
}
