package com.small.springframework.context;

import com.small.springframework.BeansException;


public interface ConfigurableApplicationContext  extends ApplicationContext {
        /**
         * 刷新容器
         * @throws BeansException
         */
        void refresh() throws BeansException;

        void registerShutdownHook();

        void close();
}
