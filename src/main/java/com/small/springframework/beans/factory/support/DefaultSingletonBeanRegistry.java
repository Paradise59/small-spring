package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.factory.DisposableBean;
import com.small.springframework.beans.factory.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

        private Map<String, Object> singletons = new ConcurrentHashMap<>();

        private Map<String, DisposableBean> disposableBeans = new ConcurrentHashMap<>();

        @Override
        public Object getSingleton(String beanName) {
                return singletons.get(beanName);
        }

        public void registerDisposableBean(String beanName, DisposableBean bean) {
                disposableBeans.put(beanName, bean);
        }

        @Override
        public void destroySingletons() {
                Object[] disposableBeanNames = this.disposableBeans.keySet().toArray();
                for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
                        Object beanName = disposableBeanNames[i];
                        DisposableBean disposableBean = disposableBeans.remove(beanName);
                        try {
                                disposableBean.destroy();
                        } catch (Exception e) {
                                throw new RuntimeException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
                        }
                }
        }

        void addSingleton(String beanName, Object singleton) {
                singletons.put(beanName, singleton);
        }
}
