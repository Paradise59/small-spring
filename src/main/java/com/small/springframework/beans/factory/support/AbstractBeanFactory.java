package com.small.springframework.beans.factory.support;

import com.small.springframework.BeanFactory;
import com.small.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.BeansException;

/**
 * 作用：实现了BeanFactory的模板方法模式
 * 实现通用的Bean获取逻辑，定义抽象的 createBean 方法供子类实现
 * 集成了单例Bean注册表功能
 */
public abstract class AbstractBeanFactory  extends DefaultSingletonBeanRegistry implements BeanFactory {
        @Override
        public Object getBean(String beanName) throws BeansException {
                Object bean = getSingleton(beanName);
                if(bean != null){
                        return bean;
                }
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                return createBean(beanName, beanDefinition);
        }

        protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

        protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
