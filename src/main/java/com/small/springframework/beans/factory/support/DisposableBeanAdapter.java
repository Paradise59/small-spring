package com.small.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.small.springframework.BeansException;
import com.small.springframework.beans.factory.DisposableBean;
import com.small.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * DisposableBeanAdapter的作用就是把**“复杂的销毁逻辑”从ApplicationContext中“隔离”**出去。它做了两件事：
 * 1. 对外统一接口： DisposableBeanAdapter 自己实现了DisposableBean接口。
 * 2. 对内封装复杂性： 它在自己的destroy()方法内部处理所有if/else的复杂判断。
 */
public class DisposableBeanAdapter implements DisposableBean {
        private final Object bean;

        private final String beanName;

        private String destroyMethodName;

        public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
                this.bean = bean;
                this.beanName = beanName;
                this.destroyMethodName = beanDefinition.getDestroyMethodName();
        }

        @Override
        public void destroy() throws Exception {
                // 1. 实现 DisposableBean 接口的 destroy 方法
                if(bean instanceof DisposableBean) {
                        ((DisposableBean) bean).destroy();
                }
                // 2. 注解配置 destroy-method {判断是为了避免二次执行销毁}
                if(StrUtil.isNotEmpty(destroyMethodName) && !(bean instanceof DisposableBean && "destory".equals(this.destroyMethodName))) {
                        Method method = bean.getClass().getMethod(destroyMethodName);
                        if(method == null) {
                                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
                        }
                        method.invoke(bean);
                }
        }
}
