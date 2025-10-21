package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.factory.BeanFactory;
import com.small.springframework.beans.factory.config.BeanDefinition;
import com.small.springframework.beans.factory.config.BeanPostProcessor;
import com.small.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.BeansException;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用：实现了BeanFactory的模板方法模式
 * 实现通用的Bean获取逻辑，定义抽象的 createBean 方法供子类实现
 * 集成了单例Bean注册表功能
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

        /** BeanPostProcessors to apply in createBean */
        private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();
        /**
         * 统一收口核心流程：在 getBean()中固化通用逻辑（如检查单例缓存）
         * 延迟具体实现：将 createBean()声明为抽象方法，强迫子类实现
         *
         * @param beanName
         * @return
         * @throws BeansException
         */
        @Override
        public Object getBean(String beanName) throws BeansException {
              return doGetBean(beanName, null);
        }


        @Override
        public Object getBean(String beanName, Object ... args) throws BeansException {
                return doGetBean(beanName, args);
        }

        @Override
        public <T> T getBean(String name, Class<T> requiredType) throws BeansException{
                return (T) getBean(name);
        }

        private <T>  T doGetBean(String beanName, Object [] args) {
                Object bean = getSingleton(beanName);
                if(bean != null){
                        return (T) bean;
                }
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                return (T) createBean(beanName, beanDefinition, args);
        }

        protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

        // 抽象方法：交给子类实现具体创建逻辑
        protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

        @Override
        public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor){
                this.beanPostProcessors.remove(beanPostProcessor);
                this.beanPostProcessors.add(beanPostProcessor);
        }

        /**
         * Return the list of BeanPostProcessors that will get applied
         * to beans created with this factory.
         */
        public List<BeanPostProcessor> getBeanPostProcessors() {
                return this.beanPostProcessors;
        }
}
