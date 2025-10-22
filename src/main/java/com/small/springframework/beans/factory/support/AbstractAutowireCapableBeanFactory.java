package com.small.springframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.small.springframework.beans.PropertyValues;
import com.small.springframework.BeansException;
import com.small.springframework.beans.PropertyValue;
import com.small.springframework.beans.factory.DisposableBean;
import com.small.springframework.beans.factory.InitializingBean;
import com.small.springframework.beans.factory.config.AutowireCapableBeanFactory;
import com.small.springframework.beans.factory.config.BeanDefinition;
import com.small.springframework.beans.factory.config.BeanPostProcessor;
import com.small.springframework.beans.factory.config.BeanReference;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 作用：实现Bean的创建逻辑，具备自动装配能力
 * 实现了 createBean 方法，负责Bean的实例化过程
 */
@Getter
@Setter
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

        private InstantiationStrategy instantiationStrategy =  new CglibSubclassingInstantiationStrategy();

        @Override
        protected Object createBean( String beanName,  BeanDefinition beanDefinition, Object[] args) throws BeansException {
                Object bean = null;
                try {
                        // 实例化Bean
                        bean = createBeanInstance(beanDefinition, beanName,  args);
                        // 填充属性
                        applyPropertyValues(beanName, bean, beanDefinition);
                        // 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法
                        bean = initializeBean(beanName, bean, beanDefinition);
                } catch (Exception e) {
                        throw new BeansException("Instantiation of bean failed", e);
                }
                // 注册实现了 DisposableBean 接口的 Bean 对象
                registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
                // 将创建好的 Bean 对象添加到单例缓存中
                addSingleton(beanName, bean);
                return bean;
        }

        private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
                if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
                        registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
                }
        }

        /**
         *  创建Bean实例
         * @param beanDefinition
         * @param beanName
         * @param args
         * @return
         */
        protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
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

        /**
         * Bean属性填充
         * @param beanName
         * @param bean
         * @param beanDefinition
         */
        protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                        String name = propertyValue.getName();
                        Object value = propertyValue.getValue();

                        if (value instanceof BeanReference) {
                                // A 依赖 B，获取 B 的实例化
                                BeanReference beanReference = (BeanReference) value;
                                value = getBean(beanReference.getBeanName());
                        }
                        BeanUtil.setFieldValue(bean, name, value);
                }
        }

        private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
                // 1. 执行 BeanPostProcessor Before 处理
                Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
                // 2. 调用初始化方法
                try {
                        invokeInitMethods(beanName, wrappedBean, beanDefinition);
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
                // 3. 执行 BeanPostProcessor After 处理
                wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
                return wrappedBean;
        }
        private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
                // 1. 实现接口 InitializingBean
                if (bean instanceof InitializingBean) {
                        ((InitializingBean) bean).afterPropertiesSet();
                }
                // 2. 注解配置 init-method {判断是为了避免二次执行初始化}
                String initMethodName = beanDefinition.getInitMethodName();
                if (StrUtil.isNotEmpty(initMethodName) && !(bean instanceof InitializingBean)) {
                        Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
                        if (null == initMethod) {
                                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
                        }
                        initMethod.invoke(bean);
                }
        };

        @Override
        public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
                Object result = existingBean;
                for (BeanPostProcessor processor : getBeanPostProcessors()) {
                        Object current = processor.postProcessBeforeInitialization(result, beanName);
                        if (null == current) return result;
                        result = current;
                }
                return result;
        }

        @Override
        public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
                Object result = existingBean;
                for (BeanPostProcessor processor : getBeanPostProcessors()) {
                        Object current = processor.postProcessAfterInitialization(result, beanName);
                        if (null == current) return result;
                        result = current;
                }
                return result;
        }
}
