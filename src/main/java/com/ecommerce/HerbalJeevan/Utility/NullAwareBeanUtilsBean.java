package com.ecommerce.HerbalJeevan.Utility;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class NullAwareBeanUtilsBean extends BeanUtilsBean {

    @Override
    public void copyProperty(Object dest, String name, Object value)
            throws IllegalAccessException, InvocationTargetException {
        
        // Handle null values
        if (value == null) {
            return;
        }

        // Handle empty strings
        if (value instanceof String && StringUtils.isBlank((String) value)) {
            return;
        }

        // Handle empty lists
        if (value instanceof List && ((List<?>) value).isEmpty()) {
            return;
        }

        // Handle Date with epoch time 0
        if (value instanceof Date && ((Date) value).getTime() == 0) {
            return;
        }

        // Handle LocalDate with MIN value
        if (value instanceof LocalDate && value.equals(LocalDate.MIN)) {
            return;
        }

        // Handle other cases (including primitive wrappers and other custom checks)
        try {
            super.copyProperty(dest, name, value);
        } catch (IllegalArgumentException e) {
            // Handle cases where value conversion might fail, like null Integer for int
            if (isPrimitive(dest, name)) {
                // Skip copying for primitive types with null values
                return;
            } else {
                throw e;
            }
        }
    }

    private boolean isPrimitive(Object dest, String name) {
        try {
            Class<?> propertyType = getPropertyUtils().getPropertyType(dest, name);
            return (propertyType != null && propertyType.isPrimitive());
        } catch (Exception e) {
            return false;
        }
    }
}
