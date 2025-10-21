package com.small.springframework.context.support;

import com.small.springframework.BeansException;
import com.small.springframework.beans.factory.ConfigurableListableBeanFactory;

public class ClassPathXmlApplicationContext  extends  AbstractXmlApplicationContext{

        private String[] configLocations;
        public ClassPathXmlApplicationContext(){}

         // 从 XML 中加载 BeanDefinition，并刷新上下文
        public ClassPathXmlApplicationContext(String configLocations) throws BeansException {
                this(new String[]{configLocations});
        }
        // 从 XML 中加载 BeanDefinition，并刷新上下文
        public ClassPathXmlApplicationContext(String[] configLocations) throws BeansException {
                this.configLocations = configLocations;
                refresh();
        }

        @Override
        protected String[] getConfigLocations() {
                return configLocations;
        }
}
