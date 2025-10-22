package com.small.springframework.beans.factory;

public interface InitializingBean {

    /**
     * Bean 属性填充完成后，执行此方法
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
