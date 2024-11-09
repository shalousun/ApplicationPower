package com.power.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.power.common.model.EnumDictionary;

/**
 * Utility class for handling operations related to enums.
 *
 * @author yu 2019/12/7.
 * @javadoc
 */
@SuppressWarnings("unchecked")
public class EnumUtil {


    /**
     * Retrieves the enumeration constant information based on the specified class, code field, and description field.
     *
     * @param clazz     The class object of the enumeration type.
     * @param codeField The name of the field or method used to obtain the code information of the enumeration constant.
     * @param descField The name of the field or method used to obtain the description information of the enumeration constant.
     * @param <T>       A subclass of EnumDictionary that specifies the type of the returned list.
     * @return A list containing the information of all enumeration constants.
     * @throws RuntimeException If the input class is not an enumeration type, or the specified codeField or descField is empty,
     *                          this exception will be thrown.
     * @apiNote This method dynamically obtains the code and description information of enumeration constants through reflection,
     * and returns a list containing the information of all enumeration constants.
     */
    public static <T extends EnumDictionary> List<T> getEnumInformation(Class<?> clazz, String codeField, String descField) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        if (!clazz.isEnum()) {
            throw new RuntimeException(clazz.getCanonicalName() + " is not an enum class.");
        }
        if (StringUtil.isEmpty(codeField) || StringUtil.isEmpty(descField)) {
            throw new RuntimeException(clazz.getCanonicalName()
                    + ":Please specify the code field name of the dictionary enumeration class and the field name that describes the dictionary code information");
        }
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        Enum<?>[] objects = enumClass.getEnumConstants();
        String valueMethodName;
        if (codeField.endsWith("()")) {
            valueMethodName = codeField.replace("()", "");
        } else {
            valueMethodName = "get" + StringUtil.firstToUpperCase(codeField);
        }
        String descMethodName;
        if (descField.endsWith("()")) {
            descMethodName = descField.replace("()", "");
        } else {
            descMethodName = "get" + StringUtil.firstToUpperCase(descField);
        }
        List<T> enumDictionaryList = new ArrayList<>();
        try {
            Method valueMethod = clazz.getDeclaredMethod(valueMethodName);
            valueMethod.setAccessible(true);
            Method descMethod = clazz.getDeclaredMethod(descMethodName);
            descMethod.setAccessible(true);
            for (Enum<?> enumType : objects) {
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
     * Retrieves information about all enum constants in a specified enum class.
     *
     * @param clazz The class object of the enum type to be queried.
     * @return A Map containing the information of all enum constants. The key is the class name of the enum,
     * and the value is a List of Maps, each Map representing the information of a single enum constant.
     * @throws RuntimeException If the input parameter is null or does not represent an enum class.
     * @apiNote This method dynamically obtains the names and values of all fields and methods defined in the enum constants,
     * as well as their ordinal values.
     */
    public static Map<String, List<Map<String, Object>>> getEnumInformation(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        if (!clazz.isEnum()) {
            throw new RuntimeException("It's not an enum class.");
        }
        Map<String, List<Map<String, Object>>> enumTypeMap = new HashMap<>();
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        List<Map<String, Object>> list = new ArrayList<>();
        String clazzName = enumClass.getName();
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        Map<String, Method> methods = getMethods(enumClass, enumConstants);
        for (Enum<?> enumType : enumConstants) {
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

    /**
     * Retrieves the names of all constants in an enumeration class as a list.
     *
     * @param enumClass The class object of the enumeration type, cannot be null.
     * @return A list containing the names of all enumeration constants. If the enumeration class does not have any constants, returns an empty list.
     * @throws RuntimeException If the input class object is null, throws a runtime exception indicating the enumeration class cannot be null.
     * @apiNote This method provides a way to obtain all the names of enumeration constants, which is useful when needing to process or display enumeration names in a list format.
     */
    public static List<String> getNames(Class<? extends Enum<?>> enumClass) {
        if (Objects.isNull(enumClass)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        if (Objects.isNull(enumConstants)) {
            return new ArrayList<>(0);
        }
        List<String> list = new ArrayList<>(enumConstants.length);
        for (Enum<?> e : enumConstants) {
            list.add(e.name());
        }
        return list;
    }

    /**
     * Retrieves the value of a field from an enumeration class using a specified method name.
     *
     * @param clazz         class
     * @param getMethodName method name
     * @return Object
     * @apiNote This method retrieves the value of a field from an enumeration class using a specified method name.
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValueByMethod(Class<?> clazz, String getMethodName) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        Enum<?>[] objects = enumClass.getEnumConstants();
        try {
            // fix method is not public
            Method method = clazz.getDeclaredMethod(getMethodName);
            method.setAccessible(true);
            for (Enum<?> enumType : objects) {
                return method.invoke(enumType);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves the value of a field from an enumeration class using a specified method name and enum constant.
     *
     * @param clazz         class
     * @param getMethodName method name
     * @param enumConstant  enum constant
     * @return Object
     * @apiNote This method retrieves the value of a field from an enumeration class using a specified method name and enum constant.
     */
    @SuppressWarnings("unchecked")
    public static Object getFieldValueByMethod(Class<?> clazz, String getMethodName, String enumConstant) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        Enum<?>[] objects = enumClass.getEnumConstants();
        try {
            // fix method is not public
            Method method = clazz.getDeclaredMethod(getMethodName);
            method.setAccessible(true);
            for (Enum<?> enumType : objects) {
                if (enumType.name().equals(enumConstant)) {
                    return method.invoke(enumType);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves the value of a field from an enumeration class using a specified field name.
     *
     * @param clazz     class
     * @param fieldName field name
     * @return Object
     * @apiNote This method retrieves the value of a field from an enumeration class using a specified field name.
     */
    public static Object getFieldValue(Class<?> clazz, String fieldName) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        try {
            Enum<?>[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants.length > 0) {
                // get field
                Field declaredField = enumClass.getDeclaredField(fieldName);
                declaredField.setAccessible(true);

                // return first enumConstants field value
                return declaredField.get(enumConstants[0]);
            }
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            // retry get method
            String methodName = "get" + (fieldName.charAt(0) + "").toUpperCase() + fieldName.substring(1);
            return getFieldValueByMethod(clazz, methodName);
        }
        return null;
    }


    /**
     * Retrieves the value of a field from an enumeration class using a specified field name.
     *
     * @param clazz     class
     * @param fieldName field name
     * @param enumConstant enum constant
     * @return Object
     * @apiNote This method retrieves the value of a field from an enumeration class using a specified field name.
     */
    public static Object getFieldValue(Class<?> clazz, String fieldName, String enumConstant) {
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("Enum class can't be null.");
        }
        Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
        try {
            Enum<?>[] enumConstants = enumClass.getEnumConstants();
            if (enumConstants.length > 0) {
                // get field
                Field declaredField = enumClass.getDeclaredField(fieldName);
                declaredField.setAccessible(true);

                for (Enum<?> enumType : enumConstants) {
                    if (enumType.name().equals(enumConstant)) {
                        return declaredField.get(enumType);
                    }
                }
            }
        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            // retry get method
            String methodName = "get" + (fieldName.charAt(0) + "").toUpperCase() + fieldName.substring(1);
            return getFieldValueByMethod(clazz, methodName, enumConstant);
        }
        return null;
    }


    /**
     * Retrieves the methods of an enumeration class.
     *
     * @param enumClass     The class object of the enumeration type.
     * @param enumConstants An array of enumeration constants.
     * @return A map containing the methods of the enumeration class. The key is the field name, and the value is the corresponding method.
     * @apiNote This method retrieves the methods of an enumeration class, excluding those that are not part of the enumeration constants.
     */
    private static Map<String, Method> getMethods(Class<Enum<?>> enumClass, Enum<?>[] enumConstants) {
        List<String> enumNames = new ArrayList<>();
        Map<String, Method> methods = new HashMap<>();
        for (Enum<?> enumType : enumConstants) {
            enumNames.add(enumType.name());
        }
        Field[] declaredFields = enumClass.getDeclaredFields();
        for (Field field : declaredFields) {
            String fieldName = field.getName();
            if (!enumNames.contains(fieldName) && !fieldName.equals("$VALUES")) {
                try {
                    Method method = enumClass.getMethod("get" + (fieldName.charAt(0) + "").toUpperCase() + fieldName.substring(1));
                    methods.put(fieldName, method);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return methods;
    }
}
