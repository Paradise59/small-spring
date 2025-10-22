package com.small.springframework.beans.factory;

import com.small.springframework.BeansException;

public interface DisposableBean {

        void destroy() throws Exception;

}
