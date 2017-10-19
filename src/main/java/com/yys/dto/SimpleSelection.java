package com.yys.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by xyr on 2017/10/19.
 */
public class SimpleSelection<T, R> implements Selection<T, R> {

    private final String field;
    private final String name;
    private final R defaultValue;

    /**
     * @param field 支持以.为分隔符的字段选择
     * @param name  显示的名称
     */
    public SimpleSelection(String field, String name) {
        this(field, name, null);
    }

    /**
     * @param field        支持以.为分隔符的字段选择
     * @param name         显示的名称
     * @param defaultValue 默认数据，如果目标数据为空，则应该显示该数据
     */
    public SimpleSelection(String field, String name, R defaultValue) {
        this.field = field;
        this.name = name;
        this.defaultValue = defaultValue;
    }

    private static Object getProperty(Object instance, String fieldName) {
        final String[] fieldNames = fieldName.split("\\.", -1);
        //if using dot notation to navigate for classes
        if (fieldNames.length > 1) {
            final String firstProperty = fieldNames[0];
            final String otherProperties =
                    StringUtils.join(fieldNames, '.', 1, fieldNames.length);
            return getProperty(getProperty(instance, firstProperty), otherProperties);
        }

        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(instance.getClass(), fieldName);
        if (propertyDescriptor == null)
            throw new IllegalArgumentException("can not find " + fieldName + " in " + instance);
        try {
            return propertyDescriptor.getReadMethod().invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public R apply(T t) {
        R result = (R) getProperty(t, field);
        if (result == null)
            return defaultValue;
        return result;
    }
}
