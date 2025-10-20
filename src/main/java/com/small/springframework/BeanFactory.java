package com.small.springframework;

import org.springframework.beans.BeansException;

/**
 * Spring IoC容器的顶层接口，定义了获取Bean的基本契约
 * 提供 getBean(String name) 方法
 */
public interface BeanFactory {
        Object getBean(String name) throws BeansException;
}
