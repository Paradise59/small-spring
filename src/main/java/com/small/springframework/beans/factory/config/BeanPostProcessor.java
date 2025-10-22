package com.small.springframework.beans.factory.config;


import com.small.springframework.BeansException;
// 在 Bean 对象实例化之后修改 Bean 对象，也可以替换 Bean 对象。
public interface BeanPostProcessor {
        /**
         * 在 Bean 对象执行初始化方法之前，执行此方法
         *
         * @param bean
         * @param beanName
         * @return
         * @throws BeansException
         */
        Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

        /**
         * 在 Bean 对象执行初始化方法之后，执行此方法
         *
         * @param bean
         * @param beanName
         * @return
         * @throws BeansException
         */
        Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
