package com.small.springframework.beans.factory;

import org.springframework.beans.BeansException;

/**
 * Spring IoC容器的顶层接口，定义了获取Bean的基本契约
 * 提供 getBean(String name) 方法
 */
public interface BeanFactory {
        // 定义获取 Bean 的规范,不关心具体实现逻辑，只声明行为
        Object getBean(String name) throws BeansException;

        /**
         *  带参数的获取 Bean 方法
         * @param name
         * @param args
         * @return
         * @throws BeansException
         */
        Object getBean(String name, Object ... args) throws BeansException;

        <T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
