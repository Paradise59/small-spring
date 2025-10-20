package com.small.springframework.beans.factory.support;

import com.small.springframework.beans.BeansException;
import com.small.springframework.beans.factory.config.BeanDefinition;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Constructor;

/**
 * 作用：实现Bean的创建逻辑，具备自动装配能力
 * 实现了 createBean 方法，负责Bean的实例化过程
 */
@Getter
@Setter
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

        private InstantiationStrategy instantiationStrategy =  new CglibSubclassingInstantiationStrategy();

        @Override
        protected Object createBean( String beanName,  BeanDefinition beanDefinition, Object[] args) throws BeansException {
                Object bean = null;
                try {
                        bean = createBeanInstance(beanDefinition, beanName,  args);
                } catch (Exception e) {
                        throw new BeansException("Instantiation of bean failed", e);
                }
                addSingleton(beanName, bean);
                return bean;
        }

        private Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
                Constructor constructorToUse = null;
                Class beanClass = beanDefinition.getBeanClass();
                Constructor[] declaredConstructors = beanClass.getDeclaredConstructors();
                for (Constructor declaredConstructor : declaredConstructors) {
                        if( null != args && declaredConstructor.getParameterTypes().length == args.length) {
                                constructorToUse = declaredConstructor;
                                break;
                        }
                }
                return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
        }
}
