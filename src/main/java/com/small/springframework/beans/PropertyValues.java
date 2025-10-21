package com.small.springframework.beans;

public class PropertyValues {
        private final java.util.List<PropertyValue> propertyValueList = new java.util.ArrayList<>();

        public void addPropertyValue(PropertyValue pv) {
                this.propertyValueList.add(pv);
        }
        public PropertyValue[]  getPropertyValues() {
                return this.propertyValueList.toArray(new PropertyValue[0]);
        }
        public PropertyValue getPropertyValue(String propertyName) {
                for (PropertyValue pv : this.propertyValueList) {
                        if (pv.getName().equals(propertyName)) {
                                return pv;
                        }
                }
                return null;
        }
}
