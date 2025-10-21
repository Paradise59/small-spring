package com.small.springframework.beans.factory.config;

import com.small.springframework.beans.PropertyValues;
import lombok.Getter;
import lombok.Setter;

/**
 *  BeanDefinition 用于描述一个 Bean 的定义信息
 */
@Getter
@Setter
public class BeanDefinition {
        private Class beanClass;

        private PropertyValues propertyValues;

        public BeanDefinition(Class beanClass) {
                this.beanClass = beanClass;
                this.propertyValues = new PropertyValues();
        }

        public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
                this.beanClass = beanClass;
                this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
        }

}
