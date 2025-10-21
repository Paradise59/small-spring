package com.small.springframework.beans.factory.config;

/**
 * BeanReference 用于表示对另一个 Bean 的引用，即一个Bean的某个属性是另一个Bean
 */
public class BeanReference {
        private final String beanName;

        public BeanReference(String beanName) {
                this.beanName = beanName;
        }

        public String getBeanName() {
                return beanName;
        }
}
