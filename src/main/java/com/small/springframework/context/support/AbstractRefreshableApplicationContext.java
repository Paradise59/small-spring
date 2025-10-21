package com.small.springframework.context.support;

import com.small.springframework.BeansException;
import com.small.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.small.springframework.beans.factory.support.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
        private DefaultListableBeanFactory beanFactory;

        @Override
        protected void refreshBeanFactory() throws BeansException {
                DefaultListableBeanFactory beanFactory = createBeanFactory();
                loadBeanDefinitions(beanFactory);
                this.beanFactory = beanFactory;
        }

        @Override
        protected ConfigurableListableBeanFactory getBeanFactory() {
                return beanFactory;
        }

        private DefaultListableBeanFactory createBeanFactory() {
                return new DefaultListableBeanFactory();
        }

        protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);
}
