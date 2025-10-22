package com.small.springframework.beans.factory.config;

/**
 * 专门管理单例Bean
 */
public interface SingletonBeanRegistry {
        Object getSingleton(String beanName);

        /**
         * 销毁单例对象
         */
        void destroySingletons();
}
