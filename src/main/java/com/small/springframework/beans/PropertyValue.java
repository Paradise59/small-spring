package com.small.springframework.beans;

import lombok.Getter;

@Getter
public class PropertyValue {
        private final String name;
        private final Object value;

        public PropertyValue(String name, Object value) {
                this.name = name;
                this.value = value;
        }
}
