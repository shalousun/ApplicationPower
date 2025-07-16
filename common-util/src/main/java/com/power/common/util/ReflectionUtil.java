package com.power.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * ReflectionUtil
 * @javadoc
 * @author yu on 2022/7/20
 */
public class ReflectionUtil {

    /**
     * Retrieving Fields, Contains Inherited Fields
     *
     * @param clazz Class
     * @return Array of Field
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }


    /**
     * Get Map of final field and value
     *
     * @param clazz Java class
     * @return Map
     * @throws IllegalAccessException IllegalAccessException
     */
    public static Map<String, String> getFinalFieldValue(Class<?> clazz) throws IllegalAccessException {
        String className = clazz.getName();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> constants = new HashMap<>();
        for (Field field : fields) {
            if (Modifier.isPrivate(field.getModifiers())) {
                continue;
            }
            if (Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                constants.put(className + "." + name, String.valueOf(field.get(null)));
            }
        }
        return constants;
    }
}
