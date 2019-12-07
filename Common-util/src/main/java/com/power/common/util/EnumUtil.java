package com.power.common.util;

import com.power.common.model.EnumDictionary;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author yu 2019/12/7.
 */
public class EnumUtil {


    /**
     * get enum values
     *
     * @param clazz      class
     * @param codeField code field
     * @param descField desc field
     * @return list
     */
    public static <T extends EnumDictionary> List<T> getEnumInformation(Class<?> clazz, String codeField, String descField) {
        if (!clazz.isEnum()) {
            throw new RuntimeException(clazz.getCanonicalName() + " is not an enum class.");
        }
        Class<Enum> enumClass = (Class<Enum>) clazz;
        Enum[] objects = enumClass.getEnumConstants();
        String valueMethodName = "get" + StringUtil.firstToUpperCase(codeField);
        String descMethodName = "get" + StringUtil.firstToUpperCase(descField);
        List<T> enumDictionaryList = new ArrayList<>();
        try {
            Method valueMethod = clazz.getMethod(valueMethodName);
            Method descMethod = clazz.getMethod(descMethodName);
            for (Enum enumType : objects) {
                Object val = valueMethod.invoke(enumType);
                Object desc = descMethod.invoke(enumType);
                EnumDictionary dataDict = new EnumDictionary();
                String type = ClassUtil.getSimpleTypeName(val);
                dataDict.setType(type);
                dataDict.setDesc(String.valueOf(desc));
                dataDict.setValue(String.valueOf(val));
                String name = enumType.name();
                int ordinal = enumType.ordinal();
                dataDict.setName(name);
                dataDict.setOrdinal(ordinal);
                enumDictionaryList.add((T) dataDict);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return enumDictionaryList;
    }

    /**
     * Get enum information
     *
     * @param clazz java class
     * @return hash map
     */
    public static Map<String, List<Map<String, Object>>> getEnumInformation(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }

        if (!clazz.isEnum()) {
            throw new RuntimeException("It's not an enum class.");
        }
        Map<String, List<Map<String, Object>>> enumTypeMap = new HashMap<>();
        Class<Enum> enumClass = (Class<Enum>) clazz;
        List<Map<String, Object>> list = new ArrayList<>();
        String clazzName = enumClass.getName();
        Enum[] enumConstants = enumClass.getEnumConstants();
        Map<String, Method> methods = getMethods(enumClass, enumConstants);
        for (Enum enumType : enumConstants) {
            Map<String, Object> map = new HashMap<>();
            for (String key : methods.keySet()) {
                try {
                    Method method = methods.get(key);
                    Object invoke = method.invoke(enumType);
                    map.put(key, invoke);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String name = enumType.name();
            int ordinal = enumType.ordinal();
            map.put("name", name);
            map.put("ordinal", ordinal);
            list.add(map);
        }
        enumTypeMap.put(clazzName, list);
        return enumTypeMap;
    }

    private static Map<String, Method> getMethods(Class<Enum> enumClass, Enum[] enumConstants) {
        List<String> enumNames = new ArrayList<>();
        Map<String, Method> methods = new HashMap<>();
        for (Enum enumType : enumConstants) {
            enumNames.add(enumType.name());
        }
        Field[] declaredFields = enumClass.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            if (!enumNames.contains(fieldName) && !fieldName.equals("$VALUES")) {
                try {
                    Method method = enumClass.getMethod("get" + (fieldName.charAt(0) + "").toUpperCase() + fieldName.substring(1, fieldName.length()));
                    methods.put(fieldName, method);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return methods;
    }
}
