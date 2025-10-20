package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

        private Map<String, Object> singletons = new ConcurrentHashMap<>();

        @Override
        public Object getSingleton(String beanName) {
                return singletons.get(beanName);
        }

        void addSingleton(String beanName, Object singleton) {
                singletons.put(beanName, singleton);
        }
}
