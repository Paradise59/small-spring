package com.small.springframework.beans;

import java.io.Serializable;

public class BeansException extends RuntimeException {
        public BeansException(String message) {
                super(message);
        }
        public BeansException(String message, Throwable cause) {
                super(message, cause);
        }
}
