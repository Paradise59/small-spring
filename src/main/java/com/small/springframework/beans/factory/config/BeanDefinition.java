package com.small.springframework.beans.factory.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeanDefinition {
        private Class beanClass;

        public BeanDefinition(Class beanClass) {
                this.beanClass = beanClass;
        }

        public Class getBean() {
                return beanClass;
        }
}
