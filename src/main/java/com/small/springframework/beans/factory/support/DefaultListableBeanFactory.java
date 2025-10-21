package com.small.springframework.beans.factory.support;

import com.small.springframework.BeansException;
import com.small.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.small.springframework.beans.factory.config.BeanDefinition;
import com.small.springframework.beans.factory.config.BeanPostProcessor;
import com.small.springframework.beans.factory.config.ConfigurableBeanFactory;


import java.util.HashMap;
import java.util.Map;

/**
 * 作用：完整的Bean工厂实现
 * 集成了Bean定义注册表功能，可以注册和管理BeanDefinition
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

        private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

        @Override
        public  BeanDefinition getBeanDefinition(String beanName) throws BeansException {
                BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
                if (beanDefinition == null) {
                        throw new BeansException("No bean named '" + beanName + "' is defined");
                }
                return beanDefinition;
        }

        @Override
        public boolean containsBeanDefinition(String beanName) {
                return beanDefinitionMap.containsKey(beanName);
        }

        @Override
        public String[] getBeanDefinitionNames() {
                return beanDefinitionMap.keySet().toArray(new String[0]);
        }

        @Override
        public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
                beanDefinitionMap.put(beanName, beanDefinition);
        }

        /**
         * 批量获取指定类型的Bean实例集合
         * @param type
         * @return
         * @param <T>
         * @throws BeansException
         */
        @Override
        public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
                Map<String, T> result = new HashMap<>();
                beanDefinitionMap.forEach((beanName, beanDefinition) -> {
                        Class beanClass = beanDefinition.getBeanClass();
                        if (type.isAssignableFrom(beanClass)) {
                                result.put(beanName, (T) getBean(beanName));
                        }
                });
                return result;
        }
        @Override
        public void preInstantiateSingletons() throws BeansException {
                beanDefinitionMap.keySet().forEach(this::getBean);
        }

}
